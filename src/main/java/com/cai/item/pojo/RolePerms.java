package com.cai.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Table(name = "role_permission")
@AllArgsConstructor
@NoArgsConstructor
public class RolePerms {

    private Integer roleId;

    private Integer permissionId;
}
