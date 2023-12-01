package com.gwf.vul.api;

import com.gwf.vul.entity.User;
import com.gwf.vul.util.Result;
import com.gwf.vul.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//swagger配置
//https://juejin.cn/post/7194271220761067557

/**
 * mvn install:install-file -Dfile=F:/vul/vul/lib/com.cmict.security-1.0.jar -DgroupId=com.cmict.security -DartifactId=com.cmict.security -Dversion=1.0 -Dpackaging=jar
 */
@Tag(name = "用户控制器")
@RestController
@RequestMapping("/users")

public class UserAPI {
    /**
     * session的字段名
     */
    private static Logger logger = LoggerFactory.getLogger(UserAPI.class);
    public static final String SESSION_NAME = "userInfo";
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param user   传入注册用户信息
     * @param errors Validation的校验错误存放对象
     * @return 注册结果
     */
    @Operation(summary = "注册接口")
    @Parameters({@Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY), @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.QUERY)})
    @PostMapping("/register")

    public Result<User> register(@RequestBody @Valid User user, BindingResult errors) {
        Result<User> result = new Result<>();
        // 如果校验有错，返回注册失败以及错误信息
        if (errors.hasErrors()) {
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }

        // 调用注册服务
        result = userService.register(user);
        return result;
    }

    /**
     * 用户登录
     *
     * @param user    传入登录用户信息
     * @param errors  Validation的校验错误存放对象
     * @param request 请求对象，用于操作session
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "登录接口")

    public Result<User> login(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        Result<User> result;
        logger.info("你好");
        // 正确示例，必须使用参数化信息的方式

        // 调用登录服务
        result = userService.login(user);
        // 如果登录成功，则设定session
        if (result.isSuccess()) {
            // 将登录成功的用户存入session
            request.getSession().setAttribute(SESSION_NAME, result.getData());
            request.getSession().setMaxInactiveInterval(1 * 60 * 60);
        }
        return result;
    }


    /**
     * 用户信息修改
     *
     * @param user    修改后用户信息对象
     * @param request 请求对象，用于操作session
     * @return 修改结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新密码接口")
    public Result<User> update(@RequestBody User user, HttpServletRequest request) {
        Result<User> result = new Result<>();
        // 从session中取出用户信息
        User sessionUser = (User) request.getSession().getAttribute(UserAPI.SESSION_NAME);
        // 创建bcrypt密码编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 对密码进行bcrypt加密
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setUsername(sessionUser.getUsername());
        user.setPassword(encryptedPassword);
        result = userService.update(user);
        // 修改成功则刷新session信息
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.getData());
        }
        return result;
    }

    /**
     * 用户登出
     *
     * @param request 请求，用于操作session
     * @return 结果对象
     */
    @GetMapping("/logout")
    @Operation(summary = "登出接口")
    public Result<Void> logout(HttpServletRequest request) {
        Result<Void> result = new Result<>();
//        request.getSession().removeAttribute(SESSION_NAME);
//        request.getSession().setAttribute(SESSION_NAME, null);
        request.getSession().invalidate();
        result.setResultSuccess("用户登出成功");
        return result;
    }

    /**
     * 查询所有用户
     *
     * @return 结果对象
     */
    @GetMapping("/getAlluser")
    @Operation(summary = "获取全部用户")
    public Result<List<User>> getAlluser(HttpServletRequest request) {
        Result<List<User>> result = new Result<>();

        Boolean isAdmin = userService.isAdmin(request);
        if (isAdmin) {
            result = userService.getAlluser();
        } else {
            result.setResultFailed("你不是管理员，不能查看用户");
        }

        return result;
    }

    /**
     * 获取全部用户和角色
     *
     * @return 结果对象
     */
    @GetMapping("/getAlluserRole")
    @Operation(summary = "获取全部用户和角色")
    public Result<List<User>> getAlluserRole(HttpServletRequest request) {
        Result<List<User>> result = new Result<>();

        Boolean isAdmin = userService.isAdmin(request);
        if (isAdmin) {
            List<User> users = userService.getAllUserRole();
            result.setResultSuccess("成功获取用户和角色");
            result.setData(users);

        } else {
            result.setResultFailed("你不是管理员，，不能查看用户和角色");
        }

        return result;
    }

    /**
     * 删除用户
     *
     * @return 结果对象
     */
    @PostMapping("/delUser")
    @Operation(summary = "删除用户")
    public Result<List<User>> delUser(@RequestBody User user, HttpServletRequest request) {
        Result<List<User>> result = new Result<>();

        Boolean isAdmin = userService.isAdmin(request);
        if (isAdmin) {
            userService.delUserByUsername(user.getUsername());
            result.setResultSuccess("成功删除用户");

        } else {
            result.setResultFailed("你不是管理员，，不能查看用户和角色");
        }

        return result;
    }
}