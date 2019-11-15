package com.supergo.manager.controller;

import com.supergo.common.pojo.Goods;
import com.supergo.feign.ApiGoodsFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 功能描述：商品信息增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:19
*/
@RestController
@RequestMapping("/goods")
@Api(value = "商品信息增删改查表现层", protocols = "http", description = "商品信息增删改查表现层")
public class GoodsController {


    /**
     * 功能描述: 商品信息调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @Autowired
    private ApiGoodsFeign apiGoodsFeign;

    /**
     * 功能描述:查询所有品牌数据
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "查询所有品牌数据")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询商品成功"),
            @ApiResponse(code = 500, message = "查询商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findAll(){
        HttpResult result = apiGoodsFeign.findAll();
        return result;
    }


    /**
     * 功能描述: 品牌管理之分页查询
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌分页查询",notes = "接受分页参数page,rows")
    @RequestMapping(value = "/query/{page}/{rows}",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询商品成功"),
            @ApiResponse(code = 500, message = "条件分页查询商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult queryBrand(@RequestBody(required = false) @ApiParam(name = "商品对象") Goods goods, @PathVariable Integer page, @PathVariable Integer rows){
        //分页查询
        return apiGoodsFeign.findPage(goods,page,rows);
    }
    /**
     * 功能描述: 品牌管理之新建
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之新建",notes = "接受对象参数brand")
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增商品品牌成功"),
            @ApiResponse(code = 500, message = "新增商品品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult saveOrUpdate(@RequestBody(required = false) @ApiParam(name = "商品品牌对象", value = "传入json格式", required = false) Goods goods){
        return  apiGoodsFeign.saveOrUpdate(goods);
    }

    /**
     * 功能描述: 品牌管理之删除
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之删除",notes = "接受对象参数ids")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除商品品牌成功"),
            @ApiResponse(code = 500, message = "批量删除商品品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的商品品牌id数组", type = "Long[]") Long[] ids){
        return apiGoodsFeign.delete(ids);
    }

    /**
     * 功能描述:品牌管理之修改回显
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之修改回显",notes = "接受对象参数brandId")
    @RequestMapping(value = "/edit/{id}",method =  RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改回显商品品牌成功"),
            @ApiResponse(code = 500, message = "修改回显商品品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult updateEdit(@PathVariable Long id){
        return apiGoodsFeign.updateEdit(id);
    }

    /**
     * 功能描述:根据关键子模糊查询品牌
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "根据关键子模糊查询品牌",notes = "接受对象参数goods")
    @RequestMapping(value = "/findByWhere",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "模糊查询商品品牌成功"),
            @ApiResponse(code = 500, message = "模糊查询商品品牌败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findByWhere(@RequestBody(required = false) @ApiParam(name = "品牌对象") Goods goods){
        return apiGoodsFeign.findByWhere(goods);
    }

}
