package com.gwf.vul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * session配置类
 */
@Configuration

public class SessionConfig {

    /**
     * 设定cookie序列化器的属性
     * 会话cookie和持久cookie
     * <p>
     * 如果不设置过期时间，则表示这个cookie生命周期为浏览器会话期间，只要关闭浏览器窗口，cookie就消失了。
     * 这种生命期为浏览器会话期的cookie被称为会话cookie。会话cookie一般不保存在硬盘上而是保存在内存里。
     * <p>
     * 如果设置了过期时间，浏览器就会把cookie保存到硬盘上，关闭后再次打开浏览器，这些cookie依然有效直到超过设定的过期时间
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID");
        // 用正则表达式配置匹配的域名，可以兼容 localhost、127.0.0.1 等各种场景
//        serializer.setDomainNamePattern("^.+?\.(\w+\.[a-z]+)$");
//         cookie生效路径
        serializer.setCookiePath("/");
        serializer.setUseHttpOnlyCookie(true);
        // 最大生命周期的单位是秒
//        serializer.setCookieMaxAge(10);
        return serializer;
    }

    /**
     * 注册序列化器
     */
    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }

}