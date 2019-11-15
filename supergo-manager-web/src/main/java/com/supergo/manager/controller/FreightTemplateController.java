package com.supergo.manager.controller;

import com.supergo.common.pojo.FreightTemplate;
import com.supergo.feign.ApiFreightTemplateFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：模板信息增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:18
*/
@RestController
@RequestMapping(value = "/freightTemplate")
@Api(value = "模板信息增删改查表现层", protocols = "http", description = "模板信息增删改查表现层")
public class FreightTemplateController {
    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:18
    */
    @Autowired
    private ApiFreightTemplateFeign apiFreightTemplateFeign;


    /**
     * 功能描述：增加FreightTemplate
     * @Param [freightTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:19
    */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    @ApiOperation(value = "新增模板", notes = "接收模板对象，新增模板")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增模板成功"),
            @ApiResponse(code = 500, message = "新增模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "模板对象", value = "传入json格式", required = false) FreightTemplate freightTemplate){
        return apiFreightTemplateFeign.add(freightTemplate);
    }


    /**
     * 功能描述：删除操作
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:19
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除模板", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除模板成功"),
            @ApiResponse(code = 500, message = "批量删除模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的模板id数组", type = "Long[]")  Long[] ids){
        return apiFreightTemplateFeign.delete(ids);
    }

    /**
     * 功能描述：修改操作
     * @Param [freightTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:19
    */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改模板", notes = "接收模板对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改模板成功"),
            @ApiResponse(code = 500, message = "修改模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "模板对象", value = "传入json格式", required = false) FreightTemplate freightTemplate){
        return apiFreightTemplateFeign.update(freightTemplate);
    }

    /**
     * 功能描述：根据ID查询
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:19
    */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询模板", notes = "接收模板")
    @ApiImplicitParam(paramType = "path", name = "id", value = "模板id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询区域成功"),
            @ApiResponse(code = 500, message = "根据id查询区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiFreightTemplateFeign.getById(id);
    }

    /**
     * 功能描述：集合查询
     * @Param [pageNum, size, freightTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:19
    */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "模板分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询模板成功"),
            @ApiResponse(code = 500, message = "条件分页查询模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "模板对象") FreightTemplate freightTemplate) {
        return apiFreightTemplateFeign.getAll(pageNum,size,freightTemplate);
    }

}
