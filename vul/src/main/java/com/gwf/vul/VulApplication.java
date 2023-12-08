package com.gwf.vul;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication


public class VulApplication {

    public static void main(String[] args) {
        SpringApplication.run(VulApplication.class, args);
        System.setProperty("jasypt.encryptor.password",System.getenv("jasypt.encryptor.password"));
    }
}
