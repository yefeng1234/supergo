package com.supergo.manager.controller;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.feign.ApiAttrFeign;
import com.supergo.http.HttpResult;
import com.supergo.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：属性增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:13
*/
@RestController
@RequestMapping("/attr")
public class AttrController {

    /**
     * 功能描述：注入属性service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:14
    */
    @Autowired
    private ApiAttrFeign apiAttrFeign;

    /**
     * 功能描述：根据ID查询属性
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:14
    */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public HttpResult findAttrById(@PathVariable("id") Integer id) {
        TbAttribute tbAttribute = apiAttrFeign.findAttrById(id);
        return HttpResult.ok(tbAttribute);
    }

    /**
     * 功能描述：根据分页查询属性
     * @Param [page, rows]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:14
    */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public HttpResult findAllAttr(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        PageResult pageResult = apiAttrFeign.findAllAttr(page, rows);
        return HttpResult.ok(pageResult);
    }

    /**
     * 功能描述：添加属性
     * @Param [attr]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:15
    */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HttpResult insertAttribute(@RequestBody TbAttribute attr) {
        Integer id = apiAttrFeign.insertAttr(attr);
        return HttpResult.ok(id);
    }

    /**
     * 功能描述：修改属性
     * @Param [attr]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:15
    */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpResult updateAttribute(@RequestBody TbAttribute attr) {
        return HttpResult.ok(apiAttrFeign.updateAttr(attr));
    }

}
