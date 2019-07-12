package com.fjc.demo.upload.exception;

/**
 * 业务处理异常
 *
 * @author fjc
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String msg;

    public BusinessException(String msg){
        this.msg = msg;
    }

    public BusinessException(String msg, Throwable e){
        super(msg, e);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
