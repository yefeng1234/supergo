package com.supergo.cart.service.impl;

import com.supergo.cart.utils.RedisUtils;
import com.supergo.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * 功能描述：购物车service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:26
*/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisUtils redisUtils;

    /*@Autowired
    private TbItemMapper tbItemMapper;*/

    @Override
    public void addCart(Integer id, Map<String, String> cartMap, List<Map<String, String>> cartList) {
        String skuId = cartMap.get("skuid");
        boolean exist = redisUtils.hHasKey(String.valueOf(id), String.valueOf(skuId));
        if (exist) {
            // 增加数量
            for (Map<String, String> map : cartList) {
                String v = map.get("skuid");
                if (v.equals(skuId)) {
                    Integer count = Integer.valueOf(map.get("count"));
                    count += Integer.valueOf(cartMap.get("count"));
                    map.put("count", count + "");
                    redisUtils.hset(String.valueOf(id), skuId, map);
                    break;
                }
            }
        } else {
            // 新增
            redisUtils.hset(String.valueOf(id), skuId, cartMap);
        }
    }

    @Override
    public List<Map<String, String>> cartList(Integer userId) {
        List<Map<String, String>> cartList = redisUtils.hvalues(String.valueOf(userId));
        return cartList;
    }

    /*@Override
    public List<Cart> mergeCartList(MergeCart mergeCart) {
        List<Cart> carts = mergeCart.getCarts();
        List<Cart> redisCarts = mergeCart.getRedisCarts();
        Cart[] redisCartsArr = redisCarts.toArray(new Cart[redisCarts.size()]);
        //循环所有Cookie购物车集合
        for (Cart cart : carts) {
            //循环每个商家购物车明细集合
            for (OrderItem orderItem : cart.getOrderItems()) {
                //把每个商品明细信息，重新往购物车中增加一次
                Integer num = orderItem.getNum();//购买数量     5
                Long itemId = orderItem.getItemId();    //购买的商品的ID  998
                //将Cookie中的购物车明细添加到Reids购物车集合中
                redisCarts = addCart(itemId, num, redisCartsArr);
            }
        }
        return redisCarts;
    }*/

    /*@Override
    @Deprecated
    public List<Cart> addCart(Long itemId, Integer num, Cart[] cartsArr) {
        List<Cart> carts = new ArrayList<>();
        Collections.addAll(carts,cartsArr);
        //防止空指针
        // carts=(carts==null? new ArrayList<Cart>() : carts);
        //查询商家信息
        Item item = tbItemMapper.selectByPrimaryKey(itemId);
        //判断该商家的购物车数据是否存在
        Cart cart = this.getSellerCart(carts, item.getSellerId());
        if(cart!=null){
            //循环该商家的购物车明细
            OrderItem orderItem = this.getCartOrderItem(cart.getOrderItems(), itemId);
            //查询明细中是否存在该商品信息
            if(orderItem!=null){
                //如果已经存在，则让购买数量增加(num)
                orderItem.setNum(orderItem.getNum()+num);
                //购买总金额=单价x数量
                double totalFee = orderItem.getNum() * orderItem.getPrice();
                orderItem.setTotalFee(totalFee);
            }else{
                //如果不存在，则创建一个新的商品明细
                orderItem = createOrderItem(item,num);
                //将新的商品购物车明细加入到该商家的购物车明细集合中
                cart.getOrderItems().add(orderItem);
            }
            //如果当前商品的个数<=0，则将该商品从商家购物车的购物明细集合中移除
            if(orderItem.getNum()<=0){
                cart.getOrderItems().remove(orderItem);
            }
            //如果该商家的购物车明细集合个数<=0，则将该商家购物车从集合中移除
            if(cart.getOrderItems().size()<=0){
                carts.remove(cart);
            }
        }else{
            //如果不存在,创建新的购物车对象
            cart = new Cart();
            cart.setSellerId(item.getSellerId());
            cart.setSellerName(item.getSeller());
            //创建新的购物车明细
            OrderItem orderItem = createOrderItem(item,num);
            //将新的购物车明细加入到新的商家购物车中
            cart.getOrderItems().add(orderItem);
            //将新的购物车对象加入到购物车集合中
            carts.add(cart);
        }
        return carts;
    }*/

    /****
     * 获取商家购物车明细
     * @param carts
     * @param sellerId
     * @return
     */
    /*private Cart getSellerCart(List<Cart> carts,String sellerId){
        for (Cart cart : carts) {
            if(cart.getSellerId().equalsIgnoreCase(sellerId)){
                return cart;
            }
        }
        return null;
    }*/

    /****
     * 获取商家的购物车明细对象
     * @return
     */
    /*private OrderItem getCartOrderItem(List<OrderItem> orderItems,Long itemId){
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getItemId().longValue()==itemId.longValue()){
                return orderItem;
            }
        }
        return  null;
    }*/

    /****
     * 构建一个OrderItem对象
     * @param item
     * @param num
     * @return
     */
    /*public OrderItem createOrderItem(Item item,Integer num){
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(item.getId());
        orderItem.setPicPath(item.getImage());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice().doubleValue());
        orderItem.setNum(num);
        //购买总金额=单价x数量
        double totalFee = num * orderItem.getPrice();
        orderItem.setTotalFee(totalFee);
        return  orderItem;
    }*/

}
