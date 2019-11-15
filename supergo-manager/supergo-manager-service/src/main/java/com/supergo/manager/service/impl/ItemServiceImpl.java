package com.supergo.manager.service.impl;

import com.supergo.common.pojo.Item;
import com.supergo.manager.mapper.ItemMapper;
import com.supergo.manager.service.ItemService;
import com.supergo.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
/**
 * 功能描述：商品service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 15:43
*/
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public int updateStatus(Long[] ids, String status) {
        Example example = new Example(Item.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        //准备修改的数据
        Item item = new Item();
        item.setStatus(status);
        return itemMapper.updateByExampleSelective(item, example);
    }
}
