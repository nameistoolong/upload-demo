package com.fjc.demo.upload.constant;

/**
 * 下载头根据agent，重新设置filename
 *
 * @author fjc
 */
public enum AgentConstant {
    /**
     * IE浏览器
     */
    MSIE("MSIE"),
    /**
     * 火狐浏览器
     */
    FIREFOX("Firefox"),
    /**
     * 谷歌浏览器
     */
    CHROME("Chrome");

    private String value;

    AgentConstant(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
