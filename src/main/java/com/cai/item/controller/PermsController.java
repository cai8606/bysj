package com.cai.item.controller;

import com.cai.item.pojo.Perms;
import com.cai.item.service.PermsService;
import com.cai.item.vo.PageResult;
import com.cai.item.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PermsController {
    @Autowired
    private PermsService service;

    @GetMapping("/perms")
    public PageResult<Perms> findByPage(@RequestParam(value = "query")String query, @RequestParam(value = "page") Integer page, @RequestParam("rows") Integer rows){
        PageResult<Perms> pageResult = service.queryByPage(query,page, rows);
        return pageResult;
    }

    @DeleteMapping("/perms/{id}")
    public Result del(@PathVariable("id") Integer id){
        Result result = service.del(id);
        return result;
    }
}
