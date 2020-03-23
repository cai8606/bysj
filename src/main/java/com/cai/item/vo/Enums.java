package com.cai.item.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Enums {
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_FAIL(404,"登录失败"),
    SUCCESS(200,"成功"),
    QUERY_SUCCESS(200,"查询成功"),
    QUERY_FAIl(404,"查询失败"),
    UPDATE_SUCCESS(200,"更新成功"),
    UPDATE_FAIL(400,"更新失败"),
    DEL_SUCCESS(200,"删除成功"),
    DEL_FAIL(400,"删除失败"),
    ADD_SUCCESS(201,"新增成功"),
    ADD_FAIL(400,"新增失败"),
    FAIL(404,"失败");

    private int code;
    private String msg;
}
