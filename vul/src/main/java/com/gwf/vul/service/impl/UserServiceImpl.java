package com.gwf.vul.service.impl;


import com.gwf.vul.api.UserAPI;
import com.gwf.vul.dao.UserDAO;
import com.gwf.vul.entity.User;
import com.gwf.vul.service.UserService;
import com.gwf.vul.util.Result;
import com.sec.password.CheckPassword;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();
        CheckPassword passwordTool = CheckPassword.getInstance();
        // 先去数据库找用户名是否存在
        User getUser = userDAO.getByUsername(user.getUsername());
        if (getUser != null) {
            result.setResultFailed("该用户名已存在");
            return result;
        }
        if (!passwordTool.isValidPassword(user.getPassword())) {
            result.setResultFailed("密码复杂度不够");
            return result;
        }
        // 创建bcrypt密码编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 对密码进行bcrypt加密
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        // 更新用户对象的密码为加密后的密码
        user.setPassword(encryptedPassword);
        // 存入数据库
        userDAO.add(user);
        // 返回成功消息
        result.setResultSuccess("注册用户成功！", user);
        return result;
    }

    @Override
    public Result<User> login(User user) {
        Result<User> result = new Result<>();
        // 去数据库查找用户
        User getUser = userDAO.getByUsername(user.getUsername());
        if (getUser == null) {
            logger.info("[{}]登录失败,用户名错误", user.getUsername());
            result.setResultFailed("用户名错误");
            return result;
        }

        // 创建bcrypt密码编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 对用户输入的密码与数据库中的密码进行比对
        if (!passwordEncoder.matches(user.getPassword(), getUser.getPassword())) {
            logger.info("[{}]登录失败,密码错误", user.getUsername());
            result.setResultFailed("密码错误");
            return result;
        }
        logger.info("[{}]登录成功", user.getUsername());
        // 设定登录成功消息
        result.setResultSuccess("登录成功", getUser);
        return result;
    }

    @Override
    public Result<User> update(User user) {
        Result<User> result = new Result<>();
        CheckPassword passwordTool = CheckPassword.getInstance();
        if (!passwordTool.isValidPassword(user.getPassword())) {
            result.setResultFailed("密码复杂度不够");
            return result;
        }
        // 存入数据库
        userDAO.update(user);
        result.setResultSuccess("修改密码成功");
        //把修改后的用户对象从数据库取出来
        result.setData(userDAO.getByUsername(user.getUsername()));
        return result;
    }

    @Override
    public Result<List<User>> getAlluser() {
        Result<List<User>> result = new Result<>();
        result.setResultSuccess("获取用户成功");
        result.setData(userDAO.getAllUser());
        return result;
    }

    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        Boolean result = null;
        // 从session中取出用户信息
        User sessionUser = (User) request.getSession().getAttribute(UserAPI.SESSION_NAME);
        // 若session中没有用户信息这说明用户未登录
        if (sessionUser.getUsername().equals("admin")) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public List<User> getAllUserRole() {
        List<User> userlist = userDAO.getAllUserRole();
        return userlist;
    }

    @Override
    public Boolean delUserByUsername(String username) {
        userDAO.delUserByUsername(username);
        return true;
    }
}