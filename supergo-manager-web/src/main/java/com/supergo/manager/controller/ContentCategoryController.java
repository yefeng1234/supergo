package com.supergo.manager.controller;

import com.supergo.common.pojo.ContentCategory;
import com.supergo.feign.ApiContentCategoryFeign;

import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：分类增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:17
*/
@RestController
@RequestMapping("/categorys")
@Api(value = "分类增删改查表现层", protocols = "http", description = "分类增删改查表现层")
public class ContentCategoryController {

    @Autowired
    private ApiContentCategoryFeign apiContentCategoryFeign;

    /****
     * 查询所有
     * URL:/contentCategory/all.shtml
     * 调用Service
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public HttpResult list(){
        return  apiContentCategoryFeign.list();
    }

    /***
     * 增加ContentCategory
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public HttpResult add(@RequestBody ContentCategory contentCategory){
        return apiContentCategoryFeign.add(contentCategory);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    public HttpResult delete(@RequestBody Integer[] ids){
        return apiContentCategoryFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/update")
    public HttpResult update(@RequestBody ContentCategory contentCategory){
        return apiContentCategoryFeign.update(contentCategory);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    public HttpResult getById(@PathVariable(value = "id") Integer id){
        return apiContentCategoryFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll")
    public HttpResult getAll(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                          @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                                          @RequestBody ContentCategory contentCategory) {
        return apiContentCategoryFeign.getAll(pageNum,size,contentCategory);
    }

}
