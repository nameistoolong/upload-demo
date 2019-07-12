package com.fjc.demo.upload.constant;

/**
 * contentType枚举
 *
 * @author fjc
 */
public enum ContentTypeConstant {
    /**
     * bpm图像
     */
    BPM(".bpm"),
    /**
     * gif动图
     */
    GIF(".gif"),
    /**
     * jpeg图片
     */
    JPEG(".jpeg"),
    /**
     * jpg图片
     */
    JPG(".jpg"),
    /**
     * png图片
     */
    PNG(".png"),
    /**
     * html网页
     */
    HTML(".html"),
    /**
     * txt文本文档
     */
    TXT(".txt"),
    /**
     * vsd
     */
    VSD(".vsd"),
    /**
     * pptx演示
     */
    PPTX(".pptx"),
    /**
     * ppt演示
     */
    PPT(".ppt"),
    /**
     * docx文档
     */
    DOCX(".docx"),
    /**
     * doc文档
     */
    DOC(".doc"),
    /**
     * xml文件
     */
    XML(".xml");

    private String value;

    ContentTypeConstant(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
