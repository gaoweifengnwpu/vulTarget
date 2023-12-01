package com.gwf.vul.service;

import com.gwf.vul.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface FileService {
    public  Result<Void> uploadFile(MultipartFile multipartFile, HttpServletRequest request);
}