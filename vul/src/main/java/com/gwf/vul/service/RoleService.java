package com.gwf.vul.service;

import com.gwf.vul.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {


    /**
     * 新增角色
     *
     * @return 新增成功记录条数
     */
    void add(Role role);


    /**
     * 根据角色名获取角色描述
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
