package com.fjc.demo.upload.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 上传service实现类
 *
 * @author fjc
 */
public interface UploadService {
    /**
     * 上传文件
     *
     * @param file       上传的文件
     * @param parentFile 上级文件夹
     * @return 返回完整的路径
     * @throws IOException io流异常
     */
    String uploadFileTest(MultipartFile file, String parentFile) throws IOException;

    /**
     * 下载本地文件
     *
     * @param targetFile 目标文件目录
     * @param request    request
     * @param response   response
     * @throws IOException io异常
     */
    void download(String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 批量下载本地文件
     *
     * @param targetFile 目标文件目录
     * @param request    request
     * @param response   response
     * @throws IOException io异常
     */
    void batchDownload(String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
