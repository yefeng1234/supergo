package com.supergo.manager.controller;
import com.supergo.common.pojo.Areas;
import com.supergo.feign.ApiAreasFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：区域信息增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:13
*/
@RestController
@RequestMapping(value = "/areas")
@Api(value = "区域信息增删改查表现层", protocols = "http")
public class AreasController {

    /**
     * 功能描述: 区域调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @Autowired
    private ApiAreasFeign apiAreasFeign;


    /**
     * 功能描述: 区域增加Areas
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增区域", notes = "接收区域对象，新增区域")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增区域成功"),
            @ApiResponse(code = 500, message = "新增区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "区域对象", value = "传入json格式", required = true) Areas areas){
        return apiAreasFeign.add(areas);
    }


    /**
     * 功能描述: 区域删除操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除区域", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除区域成功"),
            @ApiResponse(code = 500, message = "批量删除区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的区域id数组", type = "Integer[]",required = true) Integer[] ids){
        return apiAreasFeign.delete(ids);
    }

    /**
     * 功能描述: 区域修改操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改区域", notes = "接收区域对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改区域成功"),
            @ApiResponse(code = 500, message = "修改区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "区域对象", value = "传入json格式", required = true) Areas areas){
        return apiAreasFeign.update(areas);
    }

    /**
     * 功能描述: 区域根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询区域", notes = "接收区域id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "区域id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询区域成功"),
            @ApiResponse(code = 500, message = "根据id查询区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult getById(@PathVariable(value = "id") Integer id){
        return apiAreasFeign.getById(id);
    }

    /**
     * 功能描述: 条件分页查询区域信息
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:50 PM
     */
    @ApiOperation(value = "区域分页查询", notes = "接受分页参数pageNum,size")
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询区域成功"),
            @ApiResponse(code = 500, message = "条件分页查询区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "区域对象",required = true) Areas areas) {
        return apiAreasFeign.getAll(pageNum,size,areas);
    }

}
