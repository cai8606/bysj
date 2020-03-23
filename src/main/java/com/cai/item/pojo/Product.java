package com.cai.item.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name="product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id; // 主键
    private String productNum; // 编号 唯一
    private String productName; // 名称
    private String cityName; // 出发城市
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date departureTime; // 出发时间
    @Transient
    private String departureTimeStr;
    private Float productPrice; // 产品价格
    private String productDesc; // 产品描述
    private Boolean productStatus; // 状态 0 关闭 1 开启

}