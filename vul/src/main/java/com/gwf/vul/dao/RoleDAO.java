package com.gwf.vul.dao;


import com.gwf.vul.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDAO {

    /**
     * 新增角色
     *
     * @return 新增成功记录条数
     */
    void add(Role role);


    /**
     * 根据角色名获取角色
     *
     * @return Role
     */
    Role getByRoleName(String roleName);
    /**
     * 获取所有角色
     *
     * @return Role
     */
    List<Role> getAllRole();
}