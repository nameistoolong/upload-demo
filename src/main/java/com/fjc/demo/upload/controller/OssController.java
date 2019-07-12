package com.fjc.demo.upload.controller;

import com.fjc.demo.upload.exception.oss.OSSCreateBucketException;
import com.fjc.demo.upload.service.OssUploadService;
import com.fjc.demo.upload.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oss上传接口
 *
 * @author fjc
 */
@Controller
@RequestMapping("/oss")
public class OssController {

    @Resource
    private OssUploadService ossUploadService;

    /**
     * oss上传文件
     *
     * @param file       需要上传的文件
     * @param parentFile 文件的上一级目录
     * @return 结果集
     */
    @PostMapping("/streamUpload")
    @ResponseBody
    public Result<String> streamUpload(@RequestParam("file") MultipartFile file,
                                       @RequestParam(value = "folder", required = false) String parentFile) {
        if (file.isEmpty()) {
            return Result.fail("请选择需要上传的文件");
        }
        try {
            String viewUrl = ossUploadService.ossUpload(file, parentFile);
            return null != viewUrl ? Result.ok(viewUrl) : Result.fail("");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (OSSCreateBucketException e1) {
            return Result.fail(e1.getMessage());
        }
    }

    /**
     * 断点续传
     *
     * @param file       需要上传的文件
     * @param parentFile 文件的上一级目录
     * @return 结果集
     */
    @PostMapping("/breakPointUpload")
    @ResponseBody
    public Result<String> ossBreakPointUpload(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "folder", required = false) String parentFile) {
        if (file.isEmpty()) {
            return Result.fail("请选择需要上传的文件");
        }
        String viewUrl = ossUploadService.breakPointUpload(file, parentFile);
        return null != viewUrl ? Result.ok(viewUrl) : Result.fail("");
    }

    /**
     * 下载oss保存的文件
     * 访问格式：http://path?targetFile=file
     *
     * @param request  request
     * @param response response
     */
    @GetMapping("/streamDownload")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
            String targetFile = request.getParameter("targetFile");
            ossUploadService.streamDownload(request, response, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量下载oss保存的文件
     * 访问格式：http://path?targetFile=files（逗号分隔）
     *
     * @param request  request
     * @param response response
     */
    @GetMapping("/batchDownload")
    public void batchDownload(HttpServletRequest request, HttpServletResponse response) {
        try {
            String targetFile = request.getParameter("targetFile");
            ossUploadService.batchDownload(request, response, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}