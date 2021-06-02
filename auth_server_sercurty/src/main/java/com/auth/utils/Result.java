package com.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回数据格式
 *
 * @author lhy
 */
@Data
@Builder
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @Builder.Default
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @Builder.Default
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @Builder.Default
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    public Result() {
        this.setSuccess(true);
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = CommonConstant.SC_OK_0;
        this.success = true;
        this.timestamp = System.currentTimeMillis();
        return this;
    }


    public static Result<Object> ok() {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_0);
        r.setTimestamp(System.currentTimeMillis());
        r.setMessage("成功");
        return r;
    }

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_0);
        r.setMessage(msg);
        r.setTimestamp(System.currentTimeMillis());
        return r;
    }

    public static Result<Object> ok(Object data) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_0);
        r.setResult(data);
        r.setTimestamp(System.currentTimeMillis());
        return r;
    }

    public static Result<Object> error(String msg) {
        return error(CommonConstant.SC_OK_0, msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setTimestamp(System.currentTimeMillis());
        r.setSuccess(false);
        return r;
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = CommonConstant.SC_OK_0;
        this.timestamp = System.currentTimeMillis();
        this.success = false;
        return this;
    }

    /**
     * 无权限访问返回结果
     */
    public static Result<Object> noach(String msg) {
        return error(CommonConstant.SC_OK_0, msg);
    }
}
