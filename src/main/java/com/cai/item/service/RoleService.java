package com.cai.item.service;

import com.cai.item.mapper.PermsMapper;
import com.cai.item.mapper.RoleMapper;
import com.cai.item.mapper.RolePermsMapper;
import com.cai.item.pojo.Perms;
import com.cai.item.pojo.Role;
import com.cai.item.pojo.RolePerms;
import com.cai.item.vo.Enums;
import com.cai.item.vo.ResourceVo;
import com.cai.item.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermsMapper permsMapper;
    @Autowired
    private RolePermsMapper rolePermsMapper;

    public List<Role> findAll(String queryInfo){
        Example example=new Example(Role.class);
        if (StringUtils.isNotBlank(queryInfo)){
            example.createCriteria().orLike("roleName","%"+queryInfo+"%").orEqualTo("roleName",queryInfo);
        }
        List<Role> roles = roleMapper.selectByExample(example);
        return roles;
    }

    public void del(Integer id){
        roleMapper.deleteByPrimaryKey(id);
    }

    public Result findById(int id){
        Role role = roleMapper.selectByPrimaryKey(id);
        if (role==null){
            return new Result(Enums.QUERY_FAIl);
        }
        return new Result(role, Enums.QUERY_SUCCESS);
    }

    public Result update(Role role) {
        int i = roleMapper.updateByPrimaryKeySelective(role);
        if (i!=1){
            return new Result(Enums.UPDATE_FAIL);
        }
        return new Result(Enums.UPDATE_SUCCESS);
    }

    /**
     * 角色拥有的权限id
     * @return
     */
    public Map<String,List> rolePerms(Integer id){
        //返回的值
        Map<String,List> resultMap = new HashMap<>();
        //默认选中的权限id
        //List<Integer> checkPermsId=new ArrayList<>();

        Role role = roleMapper.findById(id);
        List<Perms> perms = role.getPerms();
        //System.out.println(perms);
        List<Integer> checkPermsId=new ArrayList<>();
        for (Perms perm : perms) {
            boolean notPid = isNotPid(perm.getId());
            if (notPid){
                checkPermsId.add(perm.getId());
            }
        }
        List<ResourceVo> resourceVos = this.queryPermsTree(0);

        //System.out.println(checkPermsId);
        resultMap.put("list",resourceVos);
        resultMap.put("checkPermsId",checkPermsId);
        return resultMap;
    }

    /**
     * 全部的权限信息
     * @param id
     * @return
     */
    public List<ResourceVo> queryPermsTree(int id){
        //全部的权限列表信息
        List<ResourceVo> resourceVos = new ArrayList<>();
        Example example = new Example(Perms.class);
        example.createCriteria().andEqualTo("pid",id);
        //example.setOrderByClause("sort desc");
        List<Perms> resourceList = permsMapper.selectByExample(example);

        for(Perms resource:resourceList){
            ResourceVo resourceVo = new ResourceVo();
            resourceVo.setPid(id);
            resourceVo.setId(resource.getId());
            resourceVo.setLevel(resource.getLevel());
            resourceVo.setPermsName(resource.getPermissionName());
            //resourceVo.setSort(resource.getSort());
            resourceVo.setChildren(queryPermsTree(resource.getId()));
            resourceVos.add(resourceVo);
        }
        return resourceVos;
    }

    public boolean isNotPid(int id){
        Example example = new Example(Perms.class);
        example.createCriteria().andEqualTo("pid",id);
        List<Perms> permsList = permsMapper.selectByExample(example);
        if (permsList!=null&&permsList.size()>0){
            return false;
        }
        return true;
    }

    public Result roleAddPerms(Integer roleId,String permsIds){
        //System.out.println(roleId);
        RolePerms old = new RolePerms();
        old.setRoleId(roleId);
        rolePermsMapper.delete(old);
        if (permsIds==null||permsIds==""){
            return new Result(Enums.FAIL);
        }
        String[] permsIdArr = permsIds.split(",");
        List<RolePerms> rolePermsList=new ArrayList<>();

        for (String permsId : permsIdArr) {
            RolePerms rolePerms = new RolePerms();
            rolePerms.setRoleId(roleId);
            rolePerms.setPermissionId(Integer.parseInt(permsId));
            rolePermsList.add(rolePerms);
        }

        int i = rolePermsMapper.insertList(rolePermsList);
        if (i<1){
            return new Result(Enums.FAIL);
        }
        return new Result(Enums.SUCCESS);
    }

}
