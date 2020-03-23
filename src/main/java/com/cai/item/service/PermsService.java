package com.cai.item.service;

import com.cai.item.mapper.PermsMapper;
import com.cai.item.pojo.Perms;
import com.cai.item.vo.Enums;
import com.cai.item.vo.PageResult;
import com.cai.item.vo.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PermsService {
    @Autowired
    private PermsMapper mapper;

    public PageResult<Perms> queryByPage(String query, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Perms.class);
        if (StringUtils.isNotBlank(query)){
            example.createCriteria().orLike("permissionName","%"+query+"%").orEqualTo("permissionName",query);
        }
        List<Perms> perms = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(perms)){
            return new PageResult<>(404);
        }
        PageInfo<Perms> pageInfo = new PageInfo<>(perms);
        return new PageResult<>(pageInfo.getTotal(),perms,200);
    }

    public Result del(Integer id) {
        int i = mapper.deleteByPrimaryKey(id);
        System.out.println(i);
        if (i!=1){
            return new Result(Enums.DEL_FAIL);
        }
        return new Result(Enums.DEL_SUCCESS);
    }
}
