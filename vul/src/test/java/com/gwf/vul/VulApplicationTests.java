package com.gwf.vul;

import com.gwf.vul.entity.BlogIndex;
import com.gwf.vul.entity.Role;
import com.gwf.vul.entity.User;
import com.gwf.vul.service.UserService;
import com.gwf.vul.service.impl.BlogServiceImpl;
import jakarta.annotation.Resource;
import net.minidev.json.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

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

    @Test
    public void listFilesHDFS() throws Exception {

        //1. 获取FileSystem
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.108.129:59000");
        FileSystem fileSystem = FileSystem.get(conf);
        System.out.println(fileSystem.toString());
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            String path = fileStatus.getPath().toString();
            System.out.println(path);
        }
        fileSystem.close();
    }

    //需求2: 在hdfs中创建一个文件夹
    @Test
    public void mkdirHDFS() throws Exception {
        Properties properties = System.getProperties();
        properties.setProperty("HADOOP_USER_NAME", "root");
        //1. 获取FileSystem
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.108.129:59000");
        FileSystem fileSystem = FileSystem.get(conf);
        //存在的情况下会覆盖之前的目录
        boolean success = fileSystem.mkdirs(new Path("/gwf"));
        System.out.println(success);
    }

    @Test
    public void uploadHDFS() throws Exception {
        Properties properties = System.getProperties();
        properties.setProperty("HADOOP_USER_NAME", "root");
        //1. 获取FileSystem对象
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.108.129:59000");
        FileSystem fs = FileSystem.get(conf);
        //2. 执行上传 :
        fs.copyFromLocalFile(new Path("F:\\sharekali\\ddd.txt"), new Path("/gaowefieng/"));
        //3. 释放资源
        fs.close();
    }


    @Test
    public void downLoadHDFS() throws Exception {
        Properties properties = System.getProperties();
        properties.setProperty("HADOOP_USER_NAME", "root");
        //1. 获取FileSystem对象
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.108.129:59000");
        FileSystem fileSystem = FileSystem.get(conf);
        //2. 执行下载操作: jdk
        fileSystem.copyToLocalFile(new Path("/gaowefieng/1.txt"), new Path("F:\\sharekali\\HDFS"));
        //3. 释放资源
        fileSystem.close();

    }


    @Resource
    StringEncryptor encryptor;

    @Test
    public void jacketEncrypt() {
        //加密
        String mongodb = encryptor.encrypt("mongodb://用户名:密码@IP地址:端口/库名");
        String url = encryptor.encrypt("jdbc:mysql://IP地址:端口/库名?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=true");
        String name = encryptor.encrypt("用户名");
        String password = encryptor.encrypt("123456");
        System.out.println("url 密文: " + url);
        System.out.println("mongodb 密文: " + mongodb);
        System.out.println("name 密文: " + name);
        System.out.println("password 密文: " + password);
        //解密
        String decrypt1 = encryptor.decrypt(name);
        String mongodb1 = encryptor.decrypt(mongodb);
        String decrypt2 = encryptor.decrypt(password);
        System.out.println(mongodb1 + "------------" + decrypt2);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testSend() {
        for (int i = 0; i < 5000; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("datekey", 20210610);
            map.put("userid", i);
            map.put("salaryAmount", i);
            //向kafka的big_data_topic主题推送数据
            kafkaTemplate.send("big_data_topic", JSONObject.toJSONString(map));
        }
    }
}
