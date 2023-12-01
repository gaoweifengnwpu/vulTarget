package com.gwf.vul.config;


import com.gwf.vul.api.UserAPI;
import com.gwf.vul.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器
 * preHandle：在请求到达处理器之前执行，可以用于权限验证、数据校验等操作。如果返回true，则继续执行后续操作；如果返回false，则中断请求处理。
 * postHandle：在处理器处理请求之后执行，可以用于日志记录、缓存处理等操作。
 * afterCompletion：在视图渲染之前执行，可以用于资源清理等操作。
 */
public class UserInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //客户端ip
        String clientIp = request.getRemoteAddr();
        System.out.println(clientIp);
        //客户端port
        int clientPort = request.getRemotePort();
        //请求方式
        String requestMethod = request.getMethod();
        //客户端请求URI
        String requestURI = request.getRequestURI();
        // 从session中取出用户信息
        User sessionUser = (User) request.getSession().getAttribute(UserAPI.SESSION_NAME);
        // 若session中没有用户信息这说明用户未登录
        if (sessionUser == null) {
            response.setStatus(401);
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("控制器执行完了");
    }

    // 整个请求完成后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("请求完成");
    }

}