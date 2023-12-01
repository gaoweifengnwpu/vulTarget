package com.gwf.vul.service.impl;

import com.gwf.vul.api.UserAPI;
import com.gwf.vul.entity.User;
import com.gwf.vul.service.FileService;
import com.gwf.vul.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileServiceImpl implements FileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public Result<Void> uploadFile(MultipartFile multipartFile, HttpServletRequest request) {
        // 从session中取出用户信息
        User sessionUser = (User) request.getSession().getAttribute(UserAPI.SESSION_NAME);
//        文件保存路径
        String filePath = "F:/filepath";
//        文件名
        String fileName = String.valueOf(System.currentTimeMillis()) + "_" + sessionUser.getUsername();
        File file = new File(filePath + File.separator + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);
            logger.info(file.getName(), "file upload success");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                关闭
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Result<Void> result = new Result<>();
        result.setResultSuccess("文件上传成功");
        return result;
    }
}
