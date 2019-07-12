package com.fjc.demo.upload.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.fjc.demo.upload.exception.oss.OSSCreateBucketException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

import static com.fjc.demo.upload.utils.FileUtils.*;

/**
 * oss工具类
 *
 * @author fjc
 */
public class OssUtils {

    private static final String END_POINT = "oss-cn-shanghai.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIqoqL6U9oGzaA";
    private static final String ACCESS_KEY_SECRET = "XfD8PNj9azKbjP03joNsWcl0HTT60f";
    private static final String BUCKET_NAME = "upload-demo";
    private static final Date EXPIRES = DateUtils.addDays(new Date(), 365 * 10);

    private static final OSSClient OSS_CLIENT = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

    private OssUtils() {
    }

    /**
     * 如果输入的bucket不存在，则创建bucket
     */
    private static void createBucket() {
        try {
            if (!OSS_CLIENT.doesBucketExist(BUCKET_NAME)) {
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
                OSS_CLIENT.createBucket(createBucketRequest);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new OSSCreateBucketException("创建Bucket失败,请核对Bucket名称(规则：只能包含小写字母、数字和短横线，必须以小写字母和数字开头和结尾，长度在3-63之间)");
        }
    }

    /**
     * 流式上传
     *
     * @param file       上传的文件
     * @param parentFile 上传文件的上一级目录
     * @return 访问路径
     * @throws IOException              io异常，写入oss失败
     * @throws OSSCreateBucketException 创建ossBucket失败异常
     */
    public static String streamUpload(MultipartFile file, String parentFile) throws IOException, OSSCreateBucketException {
        String objFile = ossTargetFile(file, parentFile);
        PutObjectResult result = OSS_CLIENT.putObject(new PutObjectRequest(BUCKET_NAME, objFile,
                file.getInputStream()));
        if (null != result) {
            return getViewUrl(objFile);
        }
        return null;
    }

    /**
     * 断点续传
     *
     * @param file       上传的文件
     * @param parentFile 上传文件的上一级目录
     * @return 访问路径
     */
    public static String breakPointUpload(MultipartFile file, String parentFile) {
        String objFile = ossTargetFile(file, parentFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(FileUtils.getContentType(file.getOriginalFilename()));
        try {
            UploadFileRequest fileRequest = new UploadFileRequest(BUCKET_NAME, objFile, "D:\\笔记\\就业相关资料\\就业须知 - 副本 (3).txt",
                    1024 * 1024, 5, true);
            fileRequest.setObjectMetadata(metadata);
            fileRequest.setCheckpointFile("uploadFile.ucp");
            UploadFileResult result = OSS_CLIENT.uploadFile(fileRequest);
            if (null != result) {
                return getViewUrl(objFile);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * oss流下载
     *
     * @param response      response
     * @param targetDicFile 目标文件目录
     * @throws IOException io异常
     */
    public static void streamDownload(HttpServletResponse response, String targetDicFile) throws IOException {
        OSSObject ossObject = OSS_CLIENT.getObject(BUCKET_NAME, targetDicFile);
        FileUtils.writeBytes(response, ossObject.getObjectContent());
    }

    /**
     * oss批量下载
     *
     * @param response response
     * @param files    文件路径数组
     * @throws IOException io异常
     */
    public static void batchDownload(HttpServletResponse response, String[] files) throws IOException {
        try (OutputStream outs = response.getOutputStream(); ZipOutputStream zipOutputStream = new ZipOutputStream(outs)) {
            for (String file : files) {
                OSSObject ossObject = OSS_CLIENT.getObject(BUCKET_NAME, file);
                writeBytes(zipOutputStream, ossObject.getObjectContent(), file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取访问路径
     *
     * @param key 完整的文件夹+文件名称
     * @return 访问路径
     */
    private static String getViewUrl(String key) {
        URL url = OSS_CLIENT.generatePresignedUrl(BUCKET_NAME, key, EXPIRES);
        return url.toString();
    }

    /**
     * 获取oss上保存的文件路径（只有文件夹和文件）
     *
     * @param file       上传的文件
     * @param parentFile 上传文件的上一级目录
     * @return oss上保存的文件路径（只有文件夹和文件）
     */
    private static String ossTargetFile(MultipartFile file, String parentFile) {
        createBucket();
        //设置上传的新名称
        String newFileName = renameToUUID(Objects.requireNonNull(file.getOriginalFilename()));
        //获得上一层路径
        String folder = getFolder(parentFile);
        //目标文件
        return folder + newFileName;
    }
}
