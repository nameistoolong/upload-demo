package com.fjc.demo.upload.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oss上传service
 *
 * @author fjc
 */
public interface OssUploadService {
    /**
     * oss流式上传
     *
     * @param file       上传的文件
     * @param parentFile 父文件夹
     * @return 查看路径
     * @throws IOException 流异常
     */
    String ossUpload(MultipartFile file, String parentFile) throws IOException;

    /**
     * 断点续传
     *
     * @param file       上传的文件
     * @param parentFile 父文件夹
     * @return 查看路径
     */
    String breakPointUpload(MultipartFile file, String parentFile);

    /**
     * 下载oss上文件
     *
     * @param request       request
     * @param response      response
     * @param targetDicFile 目标文件
     * @throws IOException io异常
     */
    void streamDownload(HttpServletRequest request, HttpServletResponse response, String targetDicFile) throws IOException;

    /**
     * 批量下载oss上文件
     *
     * @param request       request
     * @param response      response
     * @param targetDicFile 目标文件
     * @throws IOException io异常
     */
    void batchDownload(HttpServletRequest request, HttpServletResponse response, String targetDicFile) throws IOException;
}
