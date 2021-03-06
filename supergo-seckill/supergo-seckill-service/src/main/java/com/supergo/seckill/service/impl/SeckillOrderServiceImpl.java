package com.supergo.seckill.service.impl;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.common.pojo.SeckillOrder;
import com.supergo.seckill.mapper.SeckillGoodsMapper;
import com.supergo.seckill.mapper.SeckillOrderMapper;
import com.supergo.seckill.task.MultiThreadingCreateOrder;
import com.supergo.seckill.utils.RedisUtils;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.seckill.service.SecKillOrderService;
import com.supergo.user.utils.DateUtil;
import com.supergo.user.utils.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeckillOrderServiceImpl extends BaseServiceImpl<SeckillOrder> implements SecKillOrderService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public SeckillStatus getStatusByUserName(String userId) {
        return (SeckillStatus) redisUtils.hget("SeckillGoods_Order_Queue_Tag", userId);
    }

    @Override
    public void addSeckillOrder(String userId, Long seckillId) {
        //获取用户抢单的信息
        Object order = redisUtils.hget("SeckillOrder", userId);
        if(order!=null){
            throw new RuntimeException("存在未支付订单！");
        }
        //排队标示
        SeckillStatus checkSeckillStatus = (SeckillStatus) redisUtils.hget("SeckillGoods_Order_Queue_Tag", userId);
        if(checkSeckillStatus!=null && checkSeckillStatus.getStatus()==1){
            throw new RuntimeException("您已经在抢购商品！");
        }
        //查询商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisUtils.hget("SeckillGoods",String.valueOf(seckillId));
        //判断当前商品是否有库存
        if(seckillGoods==null || seckillGoods.getStockCount()<=0){
            throw new RuntimeException("已售罄");
        }
        //创建秒杀队列数据，秒杀排队
        SeckillStatus seckillStatus = new SeckillStatus(userId, new Date(), 1, seckillId);
        //将秒杀数据存入到队列
        redisUtils.lpush("SeckillGoods_Order_Queue_Up",seckillStatus);
        //存一个抢购标示,方便做状态查询
        redisUtils.hset("SeckillGoods_Order_Queue_Tag", userId,seckillStatus);
        //开启异步方法调用,多线程下单
        multiThreadingCreateOrder.createOrder();
    }

    @Override
    public SeckillOrder getOrderByUserName(String userName) {
        return (SeckillOrder) redisUtils.hget("SeckillOrder", userName);
    }

    @Override
    public void updateStatusSeckillOrder(String userId, String transactionId, String payTime) {
        //获取秒杀订单
        SeckillOrder seckillOrder = (SeckillOrder) redisUtils.hget("SeckillOrder", userId);
        //修改状态（支付）
        seckillOrder.setStatus("1");
        seckillOrder.setPayTime(DateUtil.str2Date(payTime));
        seckillOrder.setTransactionId(transactionId);
        //入库(MYSQL)
        seckillOrderMapper.insertSelective(seckillOrder);
        //删除Redis中订单缓存
        redisUtils.hdel("SeckillOrder", userId);
        //标识换成已支付
        SeckillStatus seckillStatus = (SeckillStatus) redisUtils.hget("SeckillGoods_Order_Queue_Tag", userId);
        seckillStatus.setStatus(5);
        redisUtils.hset("SeckillGoods_Order_Queue_Tag", userId, seckillStatus);
    }

    @Override
    public void deleteOrder(String userId) {
        //获取订单信息
        SeckillOrder seckillOrder = (SeckillOrder) redisUtils.hget("SeckillOrder", userId);
        //订单信息删除
        redisUtils.hdel("SeckillOrder", userId);
        //获取商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisUtils.hget("SeckillGoods", String.valueOf(seckillOrder.getSeckillId()));
        //如果库存为空，则从数据库查询数据，填充数据
        if(seckillGoods==null){
            seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillOrder.getSeckillId());
        }
        //库存回滚
        seckillGoods.setStockCount(seckillGoods.getStockCount()+1);
        redisUtils.hset("SeckillGoods",String.valueOf(seckillGoods.getId()),seckillGoods);
        redisUtils.hincr("SeckillGoodsCount", String.valueOf(seckillGoods.getId()),1);
        //用户排队标示
        SeckillStatus seckillStatus = (SeckillStatus) redisUtils.hget("SeckillGoods_Order_Queue_Tag",userId);
        seckillStatus.setStatus(3);
        redisUtils.hset("SeckillGoods_Order_Queue_Tag", userId,seckillStatus);
    }


}
