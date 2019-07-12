package com.fjc.demo.upload.utils;

import java.io.Serializable;

/**
 * 返回请求的结果集
 *
 * @param <T> 指定的泛型
 * @author fjc
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static Integer CODE_SUCCESS = 0;
    private final static Integer CODE_FAIL = 1;
    private final static String MSG_SUCCESS = "上传成功";
    private final static String MSG_FAIL = "上传失败";

    private Integer code;

    private String message;

    private T data;

    private Result(Integer code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(T data){
        this.code = CODE_SUCCESS;
        this.message = MSG_SUCCESS;
        this.data = data;
    }

    public static <T> Result<T> ok(T data){
        return new Result<>(data);
    }

    public static <T> Result<T> ok(Integer code, String message, T data) { return new Result<>(code, message, data); }

    public static <T> Result<T> fail(T data){
        return new Result<>(CODE_FAIL, MSG_FAIL, data);
    }

    public static <T> Result<T> fail(Integer code, String message, T data) { return new Result<>(code, message, data); }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
