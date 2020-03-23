package com.cai.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Table(name = "users_role")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    private Integer userId;

    private Integer roleId;
}
