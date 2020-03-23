package com.cai.item.controller;

import com.cai.item.pojo.Role;
import com.cai.item.service.RoleService;
import com.cai.item.vo.Enums;
import com.cai.item.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/findAll")
    public Result findAll(@RequestParam("queryInfo") String queryInfo){
        List<Role> roles = roleService.findAll(queryInfo);
        if (null == roles){
            return new Result(Enums.FAIL);
        }
        return new Result(roles,Enums.SUCCESS);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role){
        Result res = roleService.update(role);
        return res;
    }
    @DeleteMapping("/del")
    public Void del(Integer id){
       roleService.del(id);
       return null;
    }

    @GetMapping("/findById")
    public Result findById(int id){
        Result res = roleService.findById(id);
        return res;
    }

    @GetMapping("/perms")
    public Result queryPermsByRoleId( Integer id){
        Map<String, List> rolePerms = roleService.rolePerms(id);
        return new Result(rolePerms,Enums.SUCCESS);
    }

    @PostMapping("/addPerms")
    public Result roleAddPerms(Integer roleId,String permsIds){
        Result result = roleService.roleAddPerms(roleId, permsIds);
        return result;
    }
}
