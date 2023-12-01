package com.gwf.vul.dao;


import com.gwf.vul.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDAO {

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 新增成功记录条数
     */
    int add(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 修改成功记录条数
     */
    int update(User user);

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    User getById(Integer id);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getByUsername(String username);

    /**
     * 获取所有用户
     *
     * @return map
     */
    List<User> getAllUser();

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
    int delUserByUsername(String username);
}