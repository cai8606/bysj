package com.cai.item.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Result {
    private Object data;
    private int statusCode;
    private String msg;

    public Result(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public Result(Object data, Enums enums){
        this.data=data;
        this.statusCode=enums.getCode();
        this.msg=enums.getMsg();
    }

    public Result(Enums loginEnum){
        this.statusCode=loginEnum.getCode();
        this.msg=loginEnum.getMsg();
    }
}
