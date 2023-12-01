package com.gwf.vul.config;//package com.gwf.vul.config;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@Configuration
//public class CorsFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        // 这里填写你允许进行跨域的主机ip,*表示所有（正式上线时可以动态配置具体允许的域名和IP）
//        // response.setHeader("Access-Control-Allow-Origin", "*");
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        //获取来源网站
//        String originStr = request.getHeader("Origin");
//        //允许该网站进行跨域请求
//        response.setHeader("Access-Control-Allow-Origin", originStr);
//        // 允许的访问方法
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        //表示是否允许请求携带凭证信息,若要返回cookie、携带seesion等信息则将此项设置为true
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Pragma", "no-cache");
//
//        filterChain.doFilter(servletRequest, response);
//    }
//
//    @Override
//    public void destroy() {
//    }
//}