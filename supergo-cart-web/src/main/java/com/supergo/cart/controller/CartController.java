package com.supergo.cart.controller;

import com.supergo.common.pojo.Goods;
import com.supergo.common.pojo.Item;
import com.supergo.feign.ApiGoodsFeign;
import com.supergo.feign.ApiItemFeign;
import com.supergo.feign.ApiCartFeign;
import com.supergo.http.HttpResult;
import com.supergo.user.utils.BeanUtils;
import com.supergo.user.utils.CollectionsUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 功能描述：购物车
 *
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:27
 */

@RestController
@RequestMapping("/cart")
public class CartController {


    /**
     * 功能描述：注入购物车服务对象
     *
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:17
     */
    @Autowired
    private ApiCartFeign apiCartFeign;

    @Autowired
    private ApiGoodsFeign apiGoodsFeign;

    @Autowired
    private ApiItemFeign apiTbItemFeign;

    /**
     * 需求：添加购物车
     * 请求：/apiCartFeign
     * 参数：map
     * 返回值：JmypResult
     * 1）查询购物车列表
     * 2）判断购物车列表中是否存在相同的商品
     * 3）如果存在相同的商品，此商品数量相加
     * 4）否则，直接把商品添加到购物车中即可
     */
    @RequestMapping("/addCart")
    public HttpResult addCart(@RequestBody Map<String, String> cartMap) {
        return apiCartFeign.addCart(cartMap);
    }


    /**
     * 需求：查询购物车列表
     * 请求：/cartList
     * 参数：map
     * 返回值：List<Map>
     * 1）获取token
     * 2) 解析token，获取用户id
     * 3）查询该用户购物车列表数据
     */
    /**
     * 功能描述：查询购物车
     *
     * @Param []
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 14:27
     */
    @RequestMapping("/cartList")
    public HttpResult cartList() {
        try {
            //修改代码结构
            //3）查询该用户购物车列表数据
            List<Map<String, String>> maps = (List<Map<String, String>>) apiCartFeign.cartList().getData();
            //4) 查询 goods的上下架状态，以及item的库存数（是否有库存状态）
            return HttpResult.ok(this.processCartList(maps));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：业务查询(库存状态 & 商品是否在售)
     *
     * @Param [maps]
     * @Return void
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:17
     */
    private Map<String, List<Map<String, Object>>> processCartList(List<Map<String, String>> maps) throws Exception {
        List<Item> items = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> finalMap = new HashMap<>();
        if (Collections.isEmpty(maps)) {
            return new HashMap<>();
        }
        for (Map<String, String> map : maps) {
            Integer skuId = Integer.valueOf(map.get("skuid"));
            Integer count = Integer.valueOf(map.get("count"));
            //item
            HttpResult itemResult = this.apiTbItemFeign.getById(skuId.longValue());
            Item item = BeanUtils.convertMapToObject((Map) itemResult.getData(), Item.class);
            item.setCount(count);
            //goods
            // HttpResult goodsResult = this.apiGoodsFeign.getById(item.getGoodsId());
            // Goods goods = BeanUtils.convertMapToObject((Map) goodsResult.getData(), Goods.class);
            /*//库存状态｛1=有货，0=无货｝
            int stockStatus = item.getStockCount() > 0 ? 1 : 0;
            //商品是否在售｛1=在售，0=下架｝
            int saleStatus = "0".equals(goods.getIsMarketable()) ? 0 : 1;
            map.put("stockStatus", stockStatus + "");
            map.put("saleStatus", saleStatus + "");*/
            items.add(item);
        }
        List<List<Item>> lists = CollectionsUtils.groupList(items, Comparator.comparing(t -> t.getSellerId()));
        for (List<Item> itemList : lists) {
            Map<String, Object> map = new HashMap<>();
            map.put("shop", itemList.get(0).getSeller());
            map.put("orderItems", itemList);
            mapList.add(map);
        }
        finalMap.put("orders", mapList);
        return finalMap;
    }
}
