package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.AreasService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Areas;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 区域信息增删改查
 * @Author jackhu
 * @Date 8/1/2019 9:29 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/areas")
@Api(value = "/areas", description = "区域增删改查Controller层")
public class AreasController {

    @Resource(type = AreasService.class)
    private AreasService areasService;

    /**
     * @Description 添加区域信息
     * @Author jackhu
     * @Date 8/1/2019 9:30 AM
     * @Param [areas]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加区域信息", notes = "Area 对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(value = "Area 对象", required = true) Areas areas) {
        try {
            areasService.add(areas);
            return HttpResult.ok("添加区域成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加地址失败!");
    }


    /**
     * @Description 批量删除区域信息
     * @Author jackhu
     * @Date 8/1/2019 9:32 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除区域信息", notes = "Integer数组")
    @ApiImplicitParam(name = "ids", value = "要删除的区域id数组", required = true, dataType = "Integer", paramType = "query")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult delete(@RequestBody Integer[] ids) {
        try {
            areasService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }


    /**
     * @Description 修改区域信息
     * @Author jackhu
     * @Date 8/1/2019 9:35 AM
     * @Param [areas]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改区域信息", notes = "Area 对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(value = "Area 对象") Areas areas) {
        try {
            areasService.update(areas);
            return HttpResult.ok("修改区域成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改区域失败!");
    }

    /**
     * @Description 根据id查询区域信息
     * @Author jackhu
     * @Date 8/1/2019 9:37 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id", value = "查询区域的id", required = true, dataType = "Integer", paramType = "query")
    @ApiOperation(value = "根据id查询区域信息", notes = "id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult getById(@RequestParam("id") Integer id) {
        return HttpResult.ok(areasService.findOne(id));
    }

    /**
     * @Description 条件分页查询区域信息
     * @Author jackhu
     * @Date 8/1/2019 9:40 AM
     * @Param [pageNum, size, areas]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "条件分页查询区域信息", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "一页显示几条数据", required = true, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "请求资源不存在"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 403, message = "访问权限不足")
    })
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) @ApiParam(value = "分页条件Area对象") Areas areas) {
        try {
            //分页查询
            PageResult result = areasService.findPage(pageNum, size, areas);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
