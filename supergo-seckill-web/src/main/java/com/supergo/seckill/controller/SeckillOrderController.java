package com.supergo.seckill.controller;

import com.supergo.feign.ApiSeckillOrderFeign;
import com.supergo.http.HttpResult;
import com.supergo.user.utils.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *
 * @Author:shenkunlin
 * @Description:itheima
 * @date: 2019/3/27 17:32
 *
 ****/
@RestController
@RequestMapping(value = "/seckill/order")
public class SeckillOrderController {

    @Autowired
    private ApiSeckillOrderFeign apiSeckillOrderFeign;


    /***
     * 查询用户秒杀排队信息
     * url:/seckill/order/status
     * 秒杀的商品ID
     * 调用Service查询下单状态
     * @param id:秒杀查询的商品ID
     */
    @RequestMapping(value = "/status")
    public HttpResult status(Long id){
        //用户必须登录
        String userName = "123";
        if(userName.equalsIgnoreCase("anonymousUser")){
            //没登录
            return HttpResult.error("403");
        }
        //调用状态查询
        SeckillStatus seckillStatus = (SeckillStatus)apiSeckillOrderFeign.getStatusByUserName(userName).getData();
        //如果此时seckillStatus==null  || 查询的商品ID!=排队的商品ID
        if(seckillStatus==null || seckillStatus.getGoodsId().longValue()!=id.longValue()){
            //没有排队信息
            return HttpResult.error("404");
        }else if(seckillStatus.getStatus()==2){
            //如果状态为2，抢购成功，待支付
            return HttpResult.ok("抢购成功，请支付");
        }
        return HttpResult.ok(String.valueOf(seckillStatus.getStatus()));
    }

    /***
     * 添加订单信息
     * @return
     */
    @RequestMapping(value = "/add")
    public HttpResult add(Long seckillId){
        try {
            //获取用户名
            String userId = "123";
            //没有登录
            if(userId.equalsIgnoreCase("anonymousUser")){
                //用户没有登录，无权访问
                return HttpResult.error("403");
            }
            apiSeckillOrderFeign.add(userId,seckillId);
            return HttpResult.ok("抢购成功");
        } catch (Exception e) {
            return  HttpResult.ok(e.getMessage());
        }
    }
}
