package com.fjc.demo.upload.controller;

import com.fjc.demo.upload.service.UploadService;
import com.fjc.demo.upload.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 上传接口
 *
 * @author fjc
 */
@Controller
@RequestMapping("/demo")
public class UploadController {

    @Resource
    private UploadService uploadService;

    /**
     * 上传文件
     *
     * @param file       需要上传的文件
     * @param parentFile 文件的上一级目录
     * @return 结果集
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result<String> oneUpload(@RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "folder", required = false) String parentFile) {
        if (file.isEmpty()) {
            return Result.fail("请选择需要上传的文件");
        }
        try {
            String uploadPath = uploadService.uploadFileTest(file, parentFile);
            return StringUtils.isEmpty(uploadPath) ? Result.fail("非法文件名！") : Result.ok(uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("写入失败");
        } catch (NullPointerException e1) {
            return Result.fail("异常，文件为空");
        }
    }

    /**
     * 下载本地文件
     * 访问格式：http://path?targetFile=file
     *
     * @param request  request
     * @param response response
     */
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
            String targetFile = request.getParameter("targetFile");
            uploadService.download(targetFile, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量下载本地文件
     * 访问格式：http://path?targetFile=files（逗号分隔）
     *
     * @param request  request
     * @param response response
     */
    @GetMapping("/batchDownload")
    public void hatchDownload(HttpServletRequest request, HttpServletResponse response) {
        try {
            String targetFile = request.getParameter("targetFile");
            uploadService.batchDownload(targetFile, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
