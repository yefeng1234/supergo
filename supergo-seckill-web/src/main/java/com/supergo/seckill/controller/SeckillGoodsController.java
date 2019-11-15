package com.supergo.seckill.controller;

import com.supergo.feign.ApiSeckillGoodsFeign;
import com.supergo.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *
 * @Author:shenkunlin
 * @Description:itheima
 * @date: 2019/3/27 17:02
 *
 ****/
@RestController
@RequestMapping(value = "/seckill/goods")
public class SeckillGoodsController {

    @Autowired
    private ApiSeckillGoodsFeign apiSeckillGoodsFeign;


    /****
     * 查询商品的个数和剩余时间
     */
    @RequestMapping(value = "/query")
    public HttpResult queryInfo(Long seckillId){
        return apiSeckillGoodsFeign.getGoodsInfo(seckillId);
    }

    /****
     * 秒杀商品集合
     * @return
     */
    @RequestMapping(value = "/list")
    public HttpResult list(){
        return apiSeckillGoodsFeign.list();
    }

}
