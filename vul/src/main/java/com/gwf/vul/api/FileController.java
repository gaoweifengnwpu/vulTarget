package com.gwf.vul.api;

import com.gwf.vul.util.Result;
import com.gwf.vul.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "上传文件控制器")
@RestController
@RequestMapping("/files1")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result<Void> uploadFile(@RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {
        return fileService.uploadFile(multipartFile, request);
    }
}

