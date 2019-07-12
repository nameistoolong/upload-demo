package com.fjc.demo.upload.exception.oss;

import com.fjc.demo.upload.exception.BusinessException;

/**
 * oss处理异常----创建bucket异常
 *
 * @author fjc
 */
public class OSSCreateBucketException extends BusinessException {

    public OSSCreateBucketException(String msg){
        super(msg);
    }
}
