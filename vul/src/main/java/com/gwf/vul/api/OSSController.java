package com.gwf.vul.api;

import com.gwf.vul.config.MinioConfig;
import com.gwf.vul.config.MinioUtils;
import com.gwf.vul.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Tag(name = "oss上传文件控制器")
@RestController
@RequestMapping("/files")
public class OSSController {
    private static Logger log = LoggerFactory.getLogger(OSSController.class);

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public Result<Void> upload(@RequestParam("file") MultipartFile file) {
        Result<Void> result = new Result<>();
        try {
            //文件名
            String fileName = file.getOriginalFilename();
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            //类型
            String contentType = file.getContentType();
            minioUtils.uploadFile(minioConfig.getBucketName(), file, newFileName, contentType);
            result.setResultSuccess("上传成功");
        } catch (Exception e) {
            log.error(String.valueOf(e));
            result.setResultFailed("上传失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param fileName
     */
    @DeleteMapping("/")
    @Operation(summary = "删除文件")
    public Result<Void> delete(@RequestParam("fileName") String fileName) {
        Result<Void> result = new Result<>();
        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
        result.setResultSuccess("删除文件成功");
        return result;
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "获取文件信息")
    public Result<String> getFileStatusInfo(@RequestParam("fileName") String fileName) {
        Result<String> result = new Result<>();
        result.setResultSuccess(minioUtils.getFileStatusInfo(minioConfig.getBucketName(), fileName));
        return result;
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    @Operation(summary = "获取文件外链")
    public String getPresignedObjectUrl(@RequestParam("fileName") String fileName) {
        return minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), fileName);
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    @Operation(summary = "文件下载")
    public Result<Void> download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        Result<Void> result = new Result<>();
        try {
            InputStream fileInputStream = minioUtils.getObject(minioConfig.getBucketName(), fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            result.setResultFailed("下载失败");
        }
        return result;
    }


}