package com.cai.item.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perms {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String permissionName;
    private String url;
    private Integer pid;
    private String flagName;
    private Integer level;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
