package com.supergo.manager.controller;

import com.supergo.common.pojo.TbItemCat;
import com.supergo.feign.ApiItemCatFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  类目信息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/itemCat")
@Api(value = "类目信息增删改查表现层", protocols = "http", description = "类目信息增删改查表现层")
public class ItemCatController {

    /**
     * 功能描述: 类目调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiItemCatFeign apiItemCatFeign;

    /***
     * 分类列表查询
     */
    @RequestMapping(value = "/all")
    public HttpResult list() {
        return apiItemCatFeign.all();
    }

    /***
     * 根据父节点ID查询分类信息
     * URL:/itemCat/parent/{id}
     * 调用Service查询
     * 返回数据
     */
    @RequestMapping(value = "/parent/{id}")
    public HttpResult getByParentId(@PathVariable(value = "id") Long id) {
        //调用Service查询
        return apiItemCatFeign.getByParentId(id);
    }

    /***
     * 增加ItemCat
     * @param itemCat
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增类目", notes = "接收类目，新增类目信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增类目成功"),
            @ApiResponse(code = 500, message = "新增类目失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "类目信息", value = "传入json格式", required = false) TbItemCat itemCat) {
        return apiItemCatFeign.add(itemCat);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除类目", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除类目成功"),
            @ApiResponse(code = 500, message = "批量删除类目失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除类目的id数组", type = "Long[]") Long[] ids) {
        return apiItemCatFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param itemCat
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改类目", notes = "接收类目信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改类目成功"),
            @ApiResponse(code = 500, message = "修改类目失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "类目信息", value = "传入json格式", required = false) TbItemCat itemCat) {
        return apiItemCatFeign.update(itemCat);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询类目", notes = "接收类目id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "类目id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询类目成功"),
            @ApiResponse(code = 500, message = "根据id查询类目失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiItemCatFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "类目分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询类目成功"),
            @ApiResponse(code = 500, message = "条件分页查询类目失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "类目对象") TbItemCat itemCat) {
        return apiItemCatFeign.getAll(pageNum, size, itemCat);
    }

    @RequestMapping("/getByParentIdAndPage/{page}/{rows}/{id}")
    public HttpResult getByParentIdAndPage(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows, @PathVariable("id") Long id) {
        return apiItemCatFeign.getByParentIdAndPage(page, rows, id);
    }

}
