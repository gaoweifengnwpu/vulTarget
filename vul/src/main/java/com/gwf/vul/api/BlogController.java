package com.gwf.vul.api;

import org.springframework.web.client.RestTemplate;

import com.gwf.vul.entity.BlogIndex;
import com.gwf.vul.entity.User;
import com.gwf.vul.service.BlogService;
import com.gwf.vul.service.FileService;
import com.gwf.vul.util.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Tag(name = "博客控制器")
@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/saveblog")
    public Result<Void> Saveblog(@RequestBody BlogIndex blogIndex, HttpServletRequest request) {
        // 从session中取出用户信息
        User sessionUser = (User) request.getSession().getAttribute(UserAPI.SESSION_NAME);
        Result<Void> result = new Result<>();
        blogIndex.setAuthor(sessionUser.getUsername());
        blogIndex.setTitle("tangwenkuai");
        blogIndex.setCreateTime(new Date());
        blogIndex.setUpdateTime(new Date());
        blogService.save(blogIndex);
        result.setResultSuccess("保存博客成功");
        return result;
    }

    @GetMapping("/getblog")
    public Result<List<BlogIndex>> Getblog(HttpServletRequest request) throws IOException {
        // 从session中取出用户信息
        User sessionUser = (User) request.getSession().getAttribute(UserAPI.SESSION_NAME);
        Result<List<BlogIndex>> result = new Result<>();
        List<BlogIndex> list = blogService.getblogs(sessionUser.getUsername(), 1, 10);
        result.setResultSuccess("成功", list);
        return result;
    }

    /*
     * @description get方式获取入参，插入数据并发起流程
     * @date 2022/8/24 16:05
     * @params documentId
     * @return String
     */
//
    @PostMapping("/cve")
    public String submit1(@RequestBody String cveID) throws ParseException {
        String url = "https://services.nvd.nist.gov/rest/json/cves/2.0";
        Map<String, String> params = new HashMap<>();
        params.put("cveId", cveID);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        String user = response.getBody();
        return user;
    }

}
