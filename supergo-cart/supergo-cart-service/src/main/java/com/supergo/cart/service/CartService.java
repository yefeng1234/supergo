package com.supergo.cart.service;

import java.util.List;
import java.util.Map;
/**
 * 功能描述：购物车service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:27
*/
public interface CartService {

    void addCart(Integer id, Map<String,String> cartMap, List<Map<String,String>> cartList);


    List<Map<String,String>> cartList(Integer userId);

    // List<Cart> addCart(Long itemId, Integer num, Cart[] carts);
}
