package com.r.study.oauth.base.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * date 2021-06-21 14:33
 *
 * @author HeYiHui
 **/
@Data
public class R implements Serializable {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public R() {
    }
    public R(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public R(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public R(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static R ok() {
        return new R(null);
    }
    public static R ok(String message) {
        return new R(message, null);
    }
    public static R ok(Object data) {
        return new R(data);
    }
    public static R ok(String message, Object data) {
        return new R(message, data);
    }

    public static R build(Integer code, String message) {
        return new R(code, message, null);
    }

    public static R build(Integer code, String message, Object data) {
        return new R(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * JSON字符串转成 R 对象
     */
    public static R format(String json) {
        try {
            return JSON.parseObject(json, R.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}