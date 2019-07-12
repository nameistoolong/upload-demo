package com.fjc.demo.upload.service.impl;

import com.fjc.demo.upload.service.UploadService;
import com.fjc.demo.upload.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.fjc.demo.upload.utils.FileUtils.getFolder;
import static com.fjc.demo.upload.utils.FileUtils.renameToUUID;
import static com.fjc.demo.upload.utils.HttpServletUtils.setResponse;
import static com.fjc.demo.upload.utils.HttpServletUtils.setZipResponse;

/**
 * 上传service实现类
 *
 * @author fjc
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${store_url}")
    private String storeUrl;

    @Value("${view_url}")
    private String viewUrl;

    /**
     * 上传文件
     *
     * @param file       上传的文件
     * @param parentFile 上级文件夹
     * @return 返回完整的路径
     * @throws IOException io流异常
     */
    @Override
    public String uploadFileTest(MultipartFile file, String parentFile) throws IOException, NullPointerException {
        //获取文件名
        String newName = renameToUUID(Objects.requireNonNull(file.getOriginalFilename()));
        String folder = getFolder(parentFile);
        String path = storeUrl + folder;
        File target = new File(path, newName);
        //判断上级目录是否存在
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        //将文件写入目标位置
        file.transferTo(target);
        return viewUrl + folder + newName;
    }

    /**
     * 下载本地文件
     *
     * @param targetFile 目标文件目录
     * @param request    request
     * @param response   response
     * @throws IOException io异常
     */
    @Override
    public void download(String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = storeUrl + targetFile;
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        File file = new File(path);
        setResponse(request, response, fileName);
        if (file.exists()) {
            FileUtils.writeBytes(response, path);
        }
    }

    /**
     * 批量下载本地文件
     *
     * @param targetFile 目标文件目录
     * @param request    request
     * @param response   response
     * @throws IOException io异常
     */
    @Override
    public void batchDownload(String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] files = targetFile.split(",");
        for (int i = 0; i < files.length; i++) {
            files[i] = storeUrl + files[i];
        }
        setZipResponse(response);
        FileUtils.writeBytes(response, files);
    }
}
