package com.supergo.manager.controller;

import com.supergo.common.pojo.Cities;
import com.supergo.feign.ApiCitiesFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：城市信息增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:16
*/
@RestController
@RequestMapping(value = "/cities")
@Api(value = "城市信息", protocols = "http", description = "城市信息")
public class CitiesController {

    /**
     * 功能描述：注入城市service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:16
    */
    @Autowired
    private ApiCitiesFeign apiCitiesFeign;



    /**
     * 功能描述: 城市增加Cities
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增城市", notes = "接收城市，新增城市信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增城市成功"),
            @ApiResponse(code = 500, message = "新增城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "城市信息", value = "传入json格式", required = false) Cities cities){
        return apiCitiesFeign.add(cities);
    }


    /**
     * 功能描述:删除操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除城市", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除城市成功"),
            @ApiResponse(code = 500, message = "批量删除城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的城市id数组", type = "Integer[]") Integer[] ids){
        return apiCitiesFeign.delete(ids);
    }

    /**
     * 功能描述：修改操作
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改城市", notes = "接收城市信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改城市成功"),
            @ApiResponse(code = 500, message = "修改城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "城市信息", value = "传入json格式", required = false) Cities cities){
        return apiCitiesFeign.update(cities);
    }

    /**
     * 功能描述：根据ID查询
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询城市", notes = "接收城市id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "地址id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询城市成功"),
            @ApiResponse(code = 500, message = "根据id查询城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiCitiesFeign.getById(id);
    }

    /**
     * 功能描述：集合查询
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "城市分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询城市成功"),
            @ApiResponse(code = 500, message = "条件分页查询城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "城市对象") Cities cities) {
        return apiCitiesFeign.getAll(pageNum,size,cities);
    }

}
