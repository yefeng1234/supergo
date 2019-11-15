package com.supergo.manager.controller;

import com.supergo.common.pojo.Brand;
import com.supergo.feign.ApiBrandFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 功能描述：品牌增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:15
*/
@Api(value = "品牌控制器", protocols = "http", description = "品牌控制器")
@RestController
@RequestMapping("/brand")
public class BrandController {

    /**
     * 功能描述：注入品牌service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:15
    */
    @Autowired
    private ApiBrandFeign apiBrandFeign;

    /**
     * 需求：查询所有品牌数据
     * 请求：/brand/brandList
     */
    /**
     * 功能描述：查询所以品牌数据
     * @Param []
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:16
    */
    @ApiOperation(value = "查询所有品牌数据")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询品牌成功"),
            @ApiResponse(code = 500, message = "查询品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findAll(){
        HttpResult result = apiBrandFeign.findAll();
        return result;
    }

    /**
     * 功能描述: 品牌管理之分页查询
     *
     * @param:
     * @return:
     * @auther: jackhu
     * @date: 6/6/2019 15:50 PM
     */
    @ApiOperation(value = "品牌分页查询",notes = "接受分页参数page,rows")
    @RequestMapping(value = "/query/{page}/{rows}",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询品牌成功"),
            @ApiResponse(code = 500, message = "条件分页查询品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult queryBrand(@RequestBody(required = false) @ApiParam(name = "品牌对象") Brand brand, @PathVariable Integer page, @PathVariable Integer rows){
        //分页查询
        return apiBrandFeign.findPage(brand,page,rows);
    }
    /**
     * 功能描述: 品牌增加Areas
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之新建",notes = "接受对象参数brand")
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增品牌成功"),
            @ApiResponse(code = 500, message = "新增品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult saveOrUpdate(@RequestBody(required = false) @ApiParam(name = "品牌对象", value = "传入json格式", required = false) Brand brand){
        return  apiBrandFeign.saveOrUpdate(brand);


    }

    /**
     * 功能描述: 品牌管理之删除
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之删除",notes = "接受对象参数ids")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除品牌成功"),
            @ApiResponse(code = 500, message = "批量删除品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的品牌id数组", type = "Integer[]") Integer[] ids){
        System.out.println(ids);
        return apiBrandFeign.delete(ids);



    }
    /**
     * 功能描述: 品牌管理之修改回显
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之修改回显",notes = "接受对象参数brandId")
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改回显品牌成功"),
            @ApiResponse(code = 500, message = "修改回显品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult updateEdit(@PathVariable Integer id){
        return apiBrandFeign.updateEdit(id);
    }

    /**
     *
     *
     * */
    /**
     * 功能描述: 根据关键子模糊查询品牌
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "根据关键子模糊查询品牌",notes = "接受对象参数brand")
    @RequestMapping(value = "/findByWhere",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "模糊查询品牌成功"),
            @ApiResponse(code = 500, message = "模糊查询品牌败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findByWhere(@RequestBody(required = false) @ApiParam(name = "品牌对象") Brand brand){
        return apiBrandFeign.findByWhere(brand);

    }

}
