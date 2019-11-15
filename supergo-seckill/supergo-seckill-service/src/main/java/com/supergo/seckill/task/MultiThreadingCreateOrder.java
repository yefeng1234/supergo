package com.supergo.seckill.task;

import com.alibaba.fastjson.JSON;
import com.supergo.common.pojo.SeckillGoods;
import com.supergo.common.pojo.SeckillOrder;
import com.supergo.user.utils.IdWorker;
import com.supergo.user.utils.MessageInfo;
import com.supergo.seckill.mapper.SeckillGoodsMapper;
import com.supergo.seckill.mq.MessageSender;
import com.supergo.seckill.utils.RedisUtils;
import com.supergo.user.utils.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/29 14:54
 *  做多线程抢单操作
 ****/
@Component
public class MultiThreadingCreateOrder {

     @Autowired
    private RedisUtils redisUtils;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate transRedisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    // @Autowired
    private MessageSender messageSender;

    /*****
     *@Async:加上该注解后，该方法将成为多线程方法
     *       异步方法
     */
    @Async
    public void createOrder(){
        //从队列中获取排队抢购信息
        SeckillStatus seckillStatus = (SeckillStatus) redisUtils.rpop("SeckillGoods_Order_Queue_Up");
        if(seckillStatus!=null){
            //商品数量递减  JDK
            Double result = redisUtils.hincr("SeckillGoodsCount",String.valueOf(seckillStatus.getGoodsId()),-1 );
            if(result<0){
                seckillStatus.setStatus(4); //待支付
                redisUtils.hset("SeckillGoods_Order_Queue_Tag",seckillStatus.getUsername(),  seckillStatus);
                throw new RuntimeException("已售罄!");
            }
            try {
                //查询商品信息
                SeckillGoods seckillGoods = (SeckillGoods) redisUtils.hget("SeckillGoods",String.valueOf(seckillStatus.getGoodsId()));

                //创建订单
                SeckillOrder seckillOrder = new SeckillOrder();
                seckillOrder.setId(idWorker.nextId());
                seckillOrder.setSeckillId(seckillStatus.getGoodsId());
                seckillOrder.setMoney(seckillGoods.getCostPrice());
                seckillOrder.setUserId(seckillStatus.getUsername());
                seckillOrder.setCreateTime(new Date());
                seckillOrder.setStatus("0");
                //订单存入Reids
                transRedisTemplate.boundHashOps("SeckillOrder").put(seckillStatus.getUsername(),seckillOrder);
                //商品减库存 (默认只允许秒杀1个)
                seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
                //如果库存为0，将数据同步到MySQL中
                if(seckillGoods.getStockCount()==0){
                    seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                    //如果库存为0，清空Reids中该商品的缓存
                    transRedisTemplate.boundHashOps("SeckillGoods").delete(seckillStatus.getGoodsId());
                }else{
                    //库存不为空，则同步到Reids中
                    transRedisTemplate.boundHashOps("SeckillGoods").put(seckillStatus.getGoodsId(),seckillGoods);
                }
                //用户抢购标示变更
                seckillStatus.setStatus(2); //待支付
                transRedisTemplate.boundHashOps("SeckillGoods_Order_Queue_Tag").put(seckillStatus.getUsername(),seckillStatus);
                System.out.println("-----执行抢购，已存订单");
                //int q=10/0;
                //发送RockerMQ消息
                MessageInfo messageInfo = new MessageInfo(
                        1,  //创建订单
                        JSON.toJSONString(seckillStatus),
                        "Topic_Create_Seckill_Order",
                        "TagsCreateOrder",
                        UUID.randomUUID().toString());
                messageSender.sendMessage(messageInfo);
            } catch (Exception e) {

                //向上抛出异常,让Spring的事务生效
                throw new RuntimeException(e);
            }
            System.out.println("多线程结束抢单-----");
        }

    }


}
