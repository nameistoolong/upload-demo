package com.fjc.demo.upload.service.impl;

import com.fjc.demo.upload.exception.oss.OSSCreateBucketException;
import com.fjc.demo.upload.service.OssUploadService;
import com.fjc.demo.upload.utils.OssUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fjc.demo.upload.utils.HttpServletUtils.setResponse;
import static com.fjc.demo.upload.utils.HttpServletUtils.setZipResponse;

/**
 * oss上传service
 *
 * @author fjc
 */
@Service
public class OssUploadServiceImpl implements OssUploadService {

    /**
     * oss上传
     *
     * @param file       上传的文件
     * @param parentFile 父文件夹
     * @return 查看路径
     * @throws IOException 流异常
     */
    @Override
    public String ossUpload(MultipartFile file, String parentFile) throws IOException, OSSCreateBucketException {
        return OssUtils.streamUpload(file, parentFile);
    }

    /**
     * 断点续传
     *
     * @param file       上传的文件
     * @param parentFile 父文件夹
     * @return 查看路径
     */
    @Override
    public String breakPointUpload(MultipartFile file, String parentFile) {
        return OssUtils.breakPointUpload(file, parentFile);
    }

    /**
     * 下载oss上文件
     *
     * @param request       request
     * @param response      response
     * @param targetDicFile 目标文件
     * @throws IOException io异常
     */
    @Override
    public void streamDownload(HttpServletRequest request, HttpServletResponse response, String targetDicFile) throws IOException {
        String fileName = targetDicFile.substring(targetDicFile.lastIndexOf("/") + 1);
        //设置下载请求
        setResponse(request, response, fileName);
        OssUtils.streamDownload(response, targetDicFile);
    }

    /**
     * 批量下载oss上文件
     *
     * @param request       request
     * @param response      response
     * @param targetDicFile 目标文件
     * @throws IOException io异常
     */
    @Override
    public void batchDownload(HttpServletRequest request, HttpServletResponse response, String targetDicFile) throws IOException {
        String[] files = targetDicFile.split(",");
        setZipResponse(response);
        OssUtils.batchDownload(response, files);
    }
}
