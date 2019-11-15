package com.supergo.feign;

import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by on 2019/1/2.
 */
@FeignClient("supergo-cart")
public interface ApiCartFeign {

    /**
     * 需求：添加购物车
     * 请求：/addcart
     * 参数：map
     * 返回值：JmypResult
     * 1）查询购物车列表
     * 2）判断购物车列表中是否存在相同的商品
     * 3）如果存在相同的商品，此商品数量相加
     * 4）否则，直接把商品添加到购物车中即可
     */
    @RequestMapping("/cart/addCart")
    HttpResult addCart(@RequestBody(required = false) Map<String,String> cartMap);

    /**
     * 需求：查询购物车列表
     * 请求：/cartList
     * 参数：map
     * 返回值：List<Map>
     * 1）获取token
     * 2) 解析token，获取用户id
     * 3）查询该用户购物车列表数据
     */
    @RequestMapping("/cart/cartList")
    HttpResult cartList();


    /*@RequestMapping("/cart/addcart2")
    @Deprecated
    HttpResult addCart(@RequestParam("itemId") Long itemId,@RequestParam("num") Integer num,@RequestBody Cart[] carts);*/

    /**
     *  合并购物车
     * @param mergeCart
     * @return
     */
   /* @RequestMapping("/cart/mergeCartList")
    HttpResult mergeCartList(@RequestBody MergeCart mergeCart);*/
}
