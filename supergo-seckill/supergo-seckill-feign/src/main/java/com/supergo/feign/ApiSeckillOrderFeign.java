package com.supergo.feign;

import com.supergo.http.HttpResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 *
 * @Author:shenkunlin
 * @Description:itheima
 * @date: 2019/3/27 17:18
 *
 ****/
@FeignClient("jmyp-seckill")
public interface ApiSeckillOrderFeign {

    @RequestMapping("/seckill/order/getStatusByUserName")
    HttpResult getStatusByUserName(@RequestParam("userId") String userId);

    /****
     * 添加秒杀订单
     * @param userId : 用户ID
     * @param seckillId : 秒杀的商品ID
     */
    @RequestMapping("/seckill/order/add")
    HttpResult add(@RequestParam("userId") String userId,@RequestParam("seckillId") Long seckillId);

    /***
     * 根据登录名查询秒杀订单信息
     * @param userName
     * @return
     */
    @RequestMapping("/seckill/order/getOrderByUserName")
    HttpResult getOrderByUserName(@RequestParam("userName") String userName);

    /***
     * 修改秒杀订单状态
     * @param userId
     * @param transactionId
     * @param payTime
     */
    @RequestMapping("/seckill/order/updateStatus")
    HttpResult updateStatus(@RequestParam("userId") String userId, @RequestParam("transactionId") String transactionId, @RequestParam("payTime") String payTime);

    /***
     * 删除订单
     * @param userId
     */
    @RequestMapping("/seckill/order/deleteOrder")
    HttpResult deleteOrder(@RequestParam("userId") String userId);
}
