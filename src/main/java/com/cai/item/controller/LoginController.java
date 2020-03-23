package com.cai.item.controller;

import com.cai.item.vo.Enums;
import com.cai.item.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户当前数据
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try {
            subject.login(token);//执行登录方法,没有异常就ok
        } catch (Exception e) {
            return new Result(Enums.LOGIN_FAIL);
        }
        return new Result(token, Enums.LOGIN_SUCCESS);
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
//    @RequestMapping("/noauth")
//    @ResponseBody
//    public String unauthorized(){
//        return "未经授权不可访问";
//    }
}
