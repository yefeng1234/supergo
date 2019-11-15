package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.CitiesService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Cities;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 城市信息增删改查
 * @Author jackhu
 * @Date 8/1/2019 10:13 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/cities")
@Api(value = "/cities", description = "城市信息增删改查Controller层")
public class CitiesController {


    @Resource(type = CitiesService.class)
    private CitiesService citiesService;

    /**
     * @Description 添加城市信息
     * @Author jackhu
     * @Date 8/1/2019 10:10 AM
     * @Param [cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加城市信息", notes = "Cities 对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(value = "Cities 对象") Cities cities) {
        try {
            citiesService.add(cities);
            return HttpResult.ok("添加城市成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加城市失败!");
    }

    /**
     * @Description 批量删除城市信息
     * @Author jackhu
     * @Date 8/1/2019 10:13 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除城市信息", notes = "城市id数组")
    @ApiImplicitParam(name = "ids", value = "要删除的城市id数组", required = true, dataType = "Integer", paramType = "query")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult delete(@RequestBody Integer[] ids) {
        try {
            citiesService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 修改城市信息
     * * @Author  jackhu
     * @Date 8/1/2019 10:16 AM
     * @Param [cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改城市信息", notes = "Cities 对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(value = "Cities 对象") Cities cities) {
        try {
            citiesService.update(cities);
            return HttpResult.ok("修改城市成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改城市失败!");
    }

    /**
     * @Description 根据id获取城市信息
     * @Author jackhu
     * @Date 8/1/2019 10:18 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取城市信息", notes = "城市id")
    @ApiImplicitParam(name = "id", value = "城市id", required = true, dataType = "Integer", paramType = "query")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(citiesService.findOne(id));
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "条件分页查询城市信息", notes = "Cities 对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "一页显示几条数据", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) @ApiParam(value = "Cities 对象") Cities cities) {
        try {
            //分页查询
            PageResult result = citiesService.findPage(pageNum, size, cities);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
