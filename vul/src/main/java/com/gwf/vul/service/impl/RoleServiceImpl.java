package com.gwf.vul.service.impl;

import com.gwf.vul.dao.RoleDAO;
import com.gwf.vul.entity.Role;
import com.gwf.vul.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleDAO roleDAO;

    @Override
    /**
     * 新增角色
     *
     * @return 新增成功记录条数
     */ public void add(Role role) {
        roleDAO.add(role);
    }


    /**
     * 根据角色名获取角色描述
     *
     * @return Role
     */
    @Override
    public Role getByRoleName(String roleName) {
        return roleDAO.getByRoleName(roleName);
    }

    @Override
    public List<Role> getAllRole() {
        return roleDAO.getAllRole();
    }
}
