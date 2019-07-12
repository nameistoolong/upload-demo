package com.fjc.demo.upload.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.fjc.demo.upload.constant.AgentConstant.*;
import static com.fjc.demo.upload.constant.ContentTypeConstant.*;

/**
 * 文件工具类
 *
 * @author fjc
 */
public class FileUtils {

    private FileUtils() {
    }

    /**
     * 设置新文件名
     *
     * @param fileName 原来的文件名
     * @return 新文件名
     */
    public static String renameToUUID(String fileName) {
        //获取后缀 .xxx
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //设置新文件名
        fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        return fileName;
    }

    /**
     * 获得完整路径
     *
     * @param parentFile 存放文件的父文件夹
     * @return 完整路径
     */
    public static String getFolder(String parentFile) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String newDate = simpleDateFormat.format(new Date());
        if (StringUtils.isEmpty(parentFile)) {
            parentFile = "temp";
        }
        return newDate + "/" + parentFile + "/";
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    private static String getExtension(String fileName) {
        return fileName.substring(fileName.indexOf("."));
    }

    /**
     * 普通下载文件
     *
     * @param response response
     * @param path     路径
     * @throws IOException IO异常
     */
    public static void writeBytes(HttpServletResponse response, String path) throws IOException {
        try (FileInputStream fileIn = new FileInputStream(path); OutputStream outputStream = response.getOutputStream()) {
            writeBytes(fileIn, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * oss下载文件
     *
     * @param response response
     * @param in       输入流
     * @throws IOException io异常
     */
    static void writeBytes(HttpServletResponse response, InputStream in) throws IOException {
        try (OutputStream outs = response.getOutputStream()) {
            writeBytes(in, outs);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 普通批量下载文件
     *
     * @param response response
     * @param paths    多个文件路径
     * @throws IOException io异常
     */
    public static void writeBytes(HttpServletResponse response, String[] paths) throws IOException {
        try (OutputStream outs = response.getOutputStream(); ZipOutputStream zipOutputStream = new ZipOutputStream(outs)) {
            for (String path : paths) {
                FileInputStream fileIn = new FileInputStream(path);
                writeBytes(zipOutputStream, fileIn, path);
            }
        }
    }

    /**
     * 打包的通用设置
     *
     * @param zipOutputStream zip输出流
     * @param in              输入流
     * @param path            文件路径
     * @throws IOException io异常
     */
    static void writeBytes(ZipOutputStream zipOutputStream, InputStream in, String path) throws IOException {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        writeBytes(in, zipOutputStream);
        zipOutputStream.closeEntry();
        in.close();
    }

    /**
     * 写入文件通用方法
     *
     * @param in   输入流
     * @param outs 输出流
     * @throws IOException io异常
     */
    private static void writeBytes(InputStream in, OutputStream outs) throws IOException {
        byte[] bytes = new byte[1024];
        int i;
        while ((i = in.read(bytes)) > 0) {
            outs.write(bytes, 0, i);
        }
        outs.flush();
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains(MSIE.getValue())) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains(FIREFOX.getValue())) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains(CHROME.getValue())) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 获取contentType
     *
     * @param fileName 文件名
     * @return 返回contentType
     */
    static String getContentType(String fileName) {
        String extension = getExtension(fileName);
        if (extension.equalsIgnoreCase(BPM.getValue())) {
            return "image/bmp";
        }
        if (extension.equalsIgnoreCase(GIF.getValue())) {
            return "image/gif";
        }
        if (extension.equalsIgnoreCase(JPEG.getValue()) ||
                extension.equalsIgnoreCase(JPG.getValue()) ||
                extension.equalsIgnoreCase(PNG.getValue())) {
            return "image/jpeg";
        }
        if (extension.equalsIgnoreCase(HTML.getValue())) {
            return "text/html";
        }
        if (extension.equalsIgnoreCase(TXT.getValue())) {
            return "text/plain";
        }
        if (extension.equalsIgnoreCase(VSD.getValue())) {
            return "application/vnd.visio";
        }
        if (extension.equalsIgnoreCase(PPTX.getValue()) ||
                extension.equalsIgnoreCase(PPT.getValue())) {
            return "application/vnd.ms-powerpoint";
        }
        if (extension.equalsIgnoreCase(DOCX.getValue()) ||
                extension.equalsIgnoreCase(DOC.getValue())) {
            return "application/msword";
        }
        if (extension.equalsIgnoreCase(XML.getValue())) {
            return "text/xml";
        }
        return "image/jpeg";
    }
}
