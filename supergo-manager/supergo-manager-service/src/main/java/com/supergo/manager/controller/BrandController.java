package com.supergo.manager.controller;

import com.supergo.manager.service.BrandService;
import com.supergo.page.PageResult;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Brand;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @Description 品牌信息增删改查Controller层
 * @Author jackhu
 * @Date 8/1/2019 9:52 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/brand")
@Api(value = "/brand", description = "品牌信息增删改查Controller层")
public class BrandController {


    @Resource(type = BrandService.class)
    private BrandService brandService;

    /**
     * @Description 查询搜索品牌信息
     * @Author jackhu
     * @Date 8/1/2019 9:53 AM
     * @Param []
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询搜索品牌信息", notes = "无参数")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult findAll() {
        return brandService.findAll();
    }

    /**
     * @Description 分页查询所有品牌信息
     * @Author jackhu
     * @Date 8/1/2019 9:54 AM
     * @Param [brand, page, rows]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "分页查询所有品牌信息", notes = "接受分页参数page,rows")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "rows", value = "一页显示几条数据", required = true, dataType = "Integer", paramType = "path")
    })
    @RequestMapping(value = "/query/{page}/{rows}", method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult findPage(@RequestBody(required = false) Brand brand, @PathVariable Integer page, @PathVariable Integer rows) {
        try {
            //分页查询
            PageResult result = brandService.findPage(page, rows, brand);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 添加或修改品牌信息
     * @Author jackhu
     * @Date 8/1/2019 10:00 AM
     * @Param [brand]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "添加或修改品牌信息", notes = "brand 对象")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult saveOrUpdate(@RequestBody(required = false) @ApiParam(value = "Brand 对象") Brand brand) {
        try {
            //判断是添加还是修改
            if (brand.getId() != null) {
                brandService.update(brand);
            } else {
                //添加
                brandService.add(brand);
            }
            //添加修改成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //添加异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 批量删除品牌信息
     * @Author jackhu
     * @Date 8/1/2019 10:01 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "批量删除品牌信息", notes = "接受对象参数ids")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiImplicitParam(name = "ids", value = "要删除的品牌id数组", required = true, dataType = "Integer", paramType = "query")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult delete(@RequestBody(required = false) Integer[] ids) {
        try {
            brandService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 根据id查询品牌信息(修改回显)
     * @Author jackhu
     * @Date 8/1/2019 10:05 AM
     * @Param [brandId]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "品牌管理之修改回显", notes = "接受对象参数brandId")
    @RequestMapping(value = "/edit/{brandId}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "ids", value = "品牌id", required = true, dataType = "Integer", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult updateEdit(@PathVariable Integer brandId) {
        return HttpResult.ok(brandService.findOne(brandId));
    }

    /**
     * @Description 模糊查询品牌信息
     * @Author jackhu
     * @Date 8/1/2019 10:06 AM
     * @Param [brand]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "根据关键子模糊查询品牌", notes = "Brand 对象")
    @RequestMapping(value = "/findByWhere", method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult findByWhere(@RequestBody(required = false) @ApiParam(value = "Brand 对象") Brand brand) {
        return HttpResult.ok(brandService.findByWhere(brand));
    }
}
