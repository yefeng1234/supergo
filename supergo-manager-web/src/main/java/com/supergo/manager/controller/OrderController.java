package com.supergo.manager.controller;

import com.supergo.common.pojo.Order;
import com.supergo.feign.ApiOrderFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  订单信息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/order")
@Api(value = "订单信息增删改查表现层", protocols = "http", description = "订单信息增删改查表现层")
public class OrderController {

    //注入service层对象
    @Autowired
    private ApiOrderFeign apiOrderFeign;


    /***
     * 增加Order
     * @param order
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增订单", notes = "接收订单对象，新增订单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增订单成功"),
            @ApiResponse(code = 500, message = "新增订单失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "订单对象", value = "传入json格式", required = false) Order order){
        return apiOrderFeign.add(order);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除订单", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除订单成功"),
            @ApiResponse(code = 500, message = "批量删除订单失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的区域id数组", type = "Long[]") Long[] ids){
        return apiOrderFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param order
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改订单", notes = "接收订单对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改订单成功"),
            @ApiResponse(code = 500, message = "修改订单失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "订单对象", value = "传入json格式", required = false) Order order){
        return apiOrderFeign.update(order);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询订单", notes = "接收订单id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "订单id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询订单成功"),
            @ApiResponse(code = 500, message = "根据id查询订单失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiOrderFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "订单分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询订单成功"),
            @ApiResponse(code = 500, message = "条件分页查询订单失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "订单对象") Order order) {
        return apiOrderFeign.getAll(pageNum,size,order);
    }

}
