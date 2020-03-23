package com.cai.item.controller;

import com.cai.item.pojo.User;
import com.cai.item.service.UserService;
import com.cai.item.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public PageResult<User> findByPage(String query,@RequestParam(value = "page") Integer page, @RequestParam("rows") Integer rows){
        PageResult<User> pageResult = userService.queryByPage(query,page, rows);
        return pageResult;
    }

    @GetMapping("/queryById")
    public Result queryById(Integer id){
        Result result = userService.queryById(id);
        return result;
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){
        Result result = userService.saveUser(user);
        return result;
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        Result result = userService.updateUser(user);
        return result;
    }

    @GetMapping("/delUser")
    public Result deleteUser(Integer id){
        Result result = userService.deleteUser(id);
        return result;
    }

    @GetMapping("/queryRoleList")
    public Result queryRoleList(Integer id){
        List<UserRoleVo> userRoleVoList = userService.queryRoleList(id);
        return new Result(userRoleVoList,Enums.SUCCESS);
    }

    @PostMapping("/saveUserRoles")
    public Result saveUserRoles(Integer userId,String roleIds){
        Result result = userService.saveUserRoles(userId, roleIds);
        return result;
    }

    @GetMapping("/queryPermsByUsername")
    public List<RolePermsVo> queryPermsByUsername(String username){
        List<RolePermsVo> rolePermsVos = userService.queryPermsByUsername(username);
        return rolePermsVos;
    }
}
