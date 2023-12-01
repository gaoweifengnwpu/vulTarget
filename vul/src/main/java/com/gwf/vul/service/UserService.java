package com.gwf.vul.service;


import com.gwf.vul.entity.User;
import com.gwf.vul.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    Result<User> register(User user);

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return 登录结果
     */
    Result<User> login(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 修改结果
     */
    Result<User> update(User user);

    /**
     * 查询所有用户
     *
     * @return 修改结果
     */
    Result<List<User>> getAlluser();

    /**
     * 判断用户是否是管理员
     *
     * @param request 用户对象
     * @return 修改结果
     */
    Boolean isAdmin(HttpServletRequest request);

    /**
     * 获取所有用户以及对应的角色
     *
     * @return map
     */
    List<User> getAllUserRole();

    /**
     * 删除用户
     *
     * @return
     */
    Boolean delUserByUsername(String username);
}