package com.cai.item.vo;

import lombok.Data;

@Data
public class UserRoleVo {

    private Integer userId;

    private String username;

    private String roleName;

    private Integer roleId;

    private boolean check;
}
