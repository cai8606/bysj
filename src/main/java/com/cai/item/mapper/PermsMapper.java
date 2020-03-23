package com.cai.item.mapper;

import com.cai.item.pojo.Perms;
import com.cai.item.vo.RolePermsVo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermsMapper extends Mapper<Perms> {

    @Select(" select r.id as roleId,r.role_name as roleName,p.id as permsId,p.flag_name as flagName from role r" +
            " LEFT JOIN role_permission rp ON r.id = rp.role_id" +
            " LEFT JOIN permission p ON rp.permission_id = p.id where r.id IN (${_parameter})")
    List<RolePermsVo> queryUserPermsByRoleIds(String roles);

    @Select("select * from permission where id in (select permission_id from role_permission where role_id=#{id})")
    List<Perms> findPermsByRoleId(Integer id);
}
