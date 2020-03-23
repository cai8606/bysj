package com.cai.item.vo;

import lombok.Data;

import java.util.List;

/**
 * view object
 * 专门给页面使用的
 *
 * @param <T>
 */
@Data
public class PageResult<T> {
    private Long total;// 总条数
    private Long totalPage;// 总页数
    private List<T> items;// 当前页数据
    private Integer code;

    public PageResult() {
    }

    public PageResult(Integer code) {
        this.code=code;
    }

    public PageResult(Long total, List<T> items, Integer code) {
        this.total = total;
        this.items = items;
        this.code = code;
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}