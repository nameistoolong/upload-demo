package com.fjc.demo.upload.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 请求工具类
 *
 * @author fjc
 */
public class HttpServletUtils {
    /**
     * 设置请求参数
     *
     * @param request  request
     * @param response response
     * @param fileName 文件名称
     * @throws UnsupportedEncodingException 无引入包异常
     */
    public static void setResponse(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType(FileUtils.getContentType(fileName));
        //根据文件名称，设置请求头
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
    }

    public static void setZipResponse(HttpServletResponse response) {
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment; filename=\"files.zip\"");
    }
}
