package com.cai.item.service;

import com.cai.item.mapper.PermsMapper;
import com.cai.item.mapper.RoleMapper;
import com.cai.item.mapper.UserMapper;
import com.cai.item.mapper.UserRoleMapper;
import com.cai.item.pojo.Role;
import com.cai.item.pojo.User;
import com.cai.item.pojo.UserRole;
import com.cai.item.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermsMapper permsMapper;

    public Result queryById(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        if (null==user){
            return new Result(Enums.QUERY_FAIl);
        }
        return new Result(user, Enums.QUERY_SUCCESS);
    }

    public User queryByName(String username){
        User find=new User();
        find.setUsername(username);
        User user = userMapper.selectOne(find);
        return user;
    }

    public List<User> findAll(){
        List<User> users = userMapper.selectAll();
        return users;
    }

    public Result saveUser(User user){
        user.setId(null);
        user.setCreateTime(new Date());
        int i = userMapper.insert(user);
        //System.out.println(i);
        if (i<1){
            return new Result(Enums.ADD_FAIL);
        }
        return new Result(Enums.ADD_SUCCESS);
    }

    public Result updateUser(User user){
        int i = userMapper.updateByPrimaryKeySelective(user);
        //System.out.println(i);
        if (i<1){
            return new Result(Enums.UPDATE_FAIL);
        }
        return new Result(Enums.UPDATE_SUCCESS);
    }

    public Result deleteUser(Integer id) {
        int i = userMapper.deleteByPrimaryKey(id);
        System.out.println(i);
        if (i<1){
            return new Result(Enums.DEL_FAIL);
        }
        return new Result(Enums.DEL_SUCCESS);
    }

    public PageResult<User> queryByPage(String query, Integer page, Integer row) {
        PageHelper.startPage(page,row);
        Example example=new Example(User.class);
        if (StringUtils.isNotBlank(query)){
            //过滤条件
            example.createCriteria().orLike("username","%"+query+"%").orEqualTo("username");
        }
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)){
            return new PageResult<>(404);
        }
        for (User user : users) {
            List<Role> roleList = roleMapper.findRoleByUserId(user.getId());
            user.setRoles(roleList);
        }
        PageInfo<User> pageInfo = new PageInfo<>(users);

        return new PageResult<>(pageInfo.getTotal(),users,200);
    }

    public List<UserRoleVo> queryRoleList(Integer id) {
        //查询这个用户所有的角色ID
        User user = userMapper.findById(id);
        //System.out.println(user);
        List<Role> roles = user.getRoles();
        //System.out.println(roles);
        Map<Integer,Integer> userCheckRoleIdMap = new HashMap<>();
        if (roles!=null&&roles.size()>0){
            for (Role role : roles) {
                userCheckRoleIdMap.put(role.getId(),role.getId());
            }
        }

        //查询所有的角色信息
        List<Role> roleList = roleMapper.selectAll();
        List<UserRoleVo> userRoleVoList = new ArrayList<>();
        if (roleList!=null&&roleList.size()>0){
            for (Role role : roleList) {
                UserRoleVo userRoleVo = new UserRoleVo();
                userRoleVo.setUserId(id);
                if(!userCheckRoleIdMap.isEmpty()&&userCheckRoleIdMap.get(role.getId())!=null){
                    userRoleVo.setCheck(true);
                }
                userRoleVo.setRoleId(role.getId());
                userRoleVo.setRoleName(role.getRoleName());
                userRoleVoList.add(userRoleVo);
            }
        }
        return userRoleVoList;
    }

    public Result saveUserRoles(Integer userId, String roleIds) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRoleMapper.delete(userRole);
        //System.out.println(roleIds);
        if (null==roleIds||roleIds==""){
            return new Result(Enums.FAIL);
        }
        String[] roleArr = roleIds.split(",");
        List<UserRole> userRoles=new ArrayList<>();

        if (roleArr.length>0){
            for (String roleId : roleArr) {
                UserRole userRole1 = new UserRole();
                userRole1.setUserId(userId);
                userRole1.setRoleId(Integer.parseInt(roleId));
                userRoles.add(userRole1);
            }
        }

        int i = userRoleMapper.insertList(userRoles);
        //System.out.println(i);
        return new Result(Enums.SUCCESS);
    }

    public List<RolePermsVo> queryPermsByUsername(String username){
        List<RolePermsVo> rolePermsVos=new ArrayList<>();
        User user = userMapper.findByName(username);
        List<Role> roleList = user.getRoles();
        if (!CollectionUtils.isEmpty(roleList)){
            StringBuilder roleIds = new StringBuilder();
            roleList.stream().forEach(role -> roleIds.append(role.getId()+","));//roles ->{roleIds.append(roles.getId()+",");
            if (StringUtils.isNotBlank(roleIds)){
                rolePermsVos = permsMapper.queryUserPermsByRoleIds(roleIds.toString().substring(0, roleIds.lastIndexOf(",")));
            }
        }
        return rolePermsVos;
    }
}
