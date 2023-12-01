package com.gwf.vul;

import com.gwf.vul.entity.BlogIndex;
import com.gwf.vul.entity.Role;
import com.gwf.vul.entity.User;
import com.gwf.vul.service.UserService;
import com.gwf.vul.service.impl.BlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class vulApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private UserService userService;

    public void testgetAllUserRole() {
        List<User> users = userService.getAllUserRole();
        for (User user : users) {
            System.out.println("-----每个用户的信息------");
            System.out.println(user.getUsername());
            List<Role> roles = user.getRoles();
            for (Role Role : roles) {
                System.out.println(Role.getRoleName());
            }
        }
    }

    @Test
    public void testInsert() {
        BlogIndex blogIndex = new BlogIndex();
        blogIndex.setId("ss");
        blogIndex.setContent("444444444");
        blogIndex.setAuthor("tangwenkuai");
        blogIndex.setTitle("tangwenkuai");
        blogIndex.setCreateTime(new Date());
        blogIndex.setUpdateTime(new Date());
        blogService.save(blogIndex);
    }

    @Test
    public void getblog() {
        try {
            List<BlogIndex> list = blogService.getblogs("admin", 1, 1);
            for (int i = 0; i < list.size(); i++) {
                BlogIndex s = list.get(i);
                System.out.println(s.getCreateTime());
                System.out.println(s.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void api() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

// 通过 GET 方式调用，返回一个 String 值，还可以给 URL 变量设置值（也可通过 uriTemplateHandler 这个属性自定义）
//        String result = restTemplate.getForObject(
//                "https://example.com/hotels/{hotel}/rooms/{hotel}", String.class, vars);
        String url = "http://192.168.201.40:8000/register/";
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "gaoweifeng9");
        map.add("password", "gaowfeng@451W");
        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        // 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void apiHeagerCookie() {
        String url = "https://services.nvd.nist.gov/rest/json/cves/2.0?cveId=" + "CVE-2023-4555";
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
    }
}
