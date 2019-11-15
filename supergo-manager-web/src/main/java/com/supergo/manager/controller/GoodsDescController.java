package com.supergo.manager.controller;

import com.supergo.common.pojo.GoodsDesc;
import com.supergo.feign.ApiGoodsDescFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：商品规格信息增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:20
*/
@RestController
@RequestMapping(value = "/goodsDesc")
@Api(value = "商品规格信息增删改查表现层", protocols = "http", description = "商品规格信息增删改查表现层")
public class GoodsDescController {

    /**
     * 功能描述: 商品规格调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @Autowired
    private ApiGoodsDescFeign apiGoodsDescFeign;


    /**
     * 功能描述: 增加GoodsDesc
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增商品规格", notes = "接收规格，新增商品规格信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增商品规格成功"),
            @ApiResponse(code = 500, message = "新增商品规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "商品规格信息", value = "传入json格式", required = false)  GoodsDesc goodsDesc){
        return apiGoodsDescFeign.add(goodsDesc);
    }


    /**
     * 功能描述:  删除操作
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除商品规格", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除商品规格成功"),
            @ApiResponse(code = 500, message = "批量删除商品规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的地址id数组", type = "Long[]") Long[] ids){
        return apiGoodsDescFeign.delete(ids);
    }

    /**
     * 功能描述: 修改操作
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改商品规格", notes = "接收商品规格信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改商品规格成功"),
            @ApiResponse(code = 500, message = "修改商品规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "商品规格信息", value = "传入json格式", required = false) GoodsDesc goodsDesc){
        return apiGoodsDescFeign.update(goodsDesc);
    }

     /**
     * 功能描述:  根据ID查询
      * @auther: jackhu
      * @date: 6/6/2019 15:43 AM
      */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询商品规格", notes = "接收地址id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "商品规格id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询商品规格成功"),
            @ApiResponse(code = 500, message = "根据id查询商品规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiGoodsDescFeign.getById(id);
    }

    /**
     * 功能描述: 集合查询
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "商品规格分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询商品规格成功"),
            @ApiResponse(code = 500, message = "条件分页查询商品规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "商品规格对象") GoodsDesc goodsDesc) {
        return apiGoodsDescFeign.getAll(pageNum,size,goodsDesc);
    }

}
