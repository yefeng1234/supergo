package com.supergo.manager.service;

import com.supergo.common.pojo.Item;
import com.supergo.service.base.BaseService;

/**
 * 功能描述：商品service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 15:43
*/
public interface ItemService extends BaseService<Item> {

    int updateStatus(Long[] ids, String status);

}
