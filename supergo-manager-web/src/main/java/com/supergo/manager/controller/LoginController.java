package com.supergo.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：获取用户登录名
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:20
*/
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    /*****
     * 获取用户登录名
     * URL:/login/name
     * 获取用户名，返回用户名
     */
    @RequestMapping(value = "/name")
    public String name(){
        return "zhangsan";
    }
}
