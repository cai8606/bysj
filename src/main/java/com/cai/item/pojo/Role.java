package com.cai.item.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String roleName;
    private String roleDesc;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @Transient
    private List<Perms> perms;
}
