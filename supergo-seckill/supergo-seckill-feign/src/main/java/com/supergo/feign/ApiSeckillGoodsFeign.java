package com.supergo.feign;

import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 *
 * @Author:shenkunlin
 * @Description:itheima
 * @date: 2019/3/27 17:00
 *
 ****/
@FeignClient("jmyp-seckill")
public interface ApiSeckillGoodsFeign {

    /****
     * 秒杀商品集合
     */
    @RequestMapping("/seckill/goods/list")
    HttpResult list();


    /***
     * 查询商品的个数和剩余活动时间
     * @param seckillId:秒杀商品的ID
     */
    @RequestMapping("/seckill/goods/getGoodsInfo")
    HttpResult getGoodsInfo(@RequestParam("seckillId") Long seckillId);

}
