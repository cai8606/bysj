package com.cai.item.vo;

import lombok.Data;

import java.util.List;


@Data
public class ResourceVo {
    private Integer id;

    private Integer pid;

    private String permsName;

    private Integer level;

    private List<ResourceVo> children;

}
