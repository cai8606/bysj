package com.cai.item.mapper;

import com.cai.item.pojo.Role;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {
    @Select("select * from role where id in (select role_id from users_role where user_id=#{userId})")
    List<Role> findRoleByUserId(Integer userId);

    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleDesc", column = "role_desc"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "perms",column = "id",javaType = List.class,
                    many = @Many(select = "com.cai.item.mapper.PermsMapper.findPermsByRoleId",fetchType = FetchType.LAZY))
    })
    Role findById(Integer id);
}
