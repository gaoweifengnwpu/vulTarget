package com.gwf.vul.api;

import com.gwf.vul.entity.Role;
import com.gwf.vul.service.RoleService;
import com.gwf.vul.service.UserService;
import com.gwf.vul.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色控制器")
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 插入角色
     *
     * @param role 请求，用于操作session
     * @return 结果对象
     */
    @Operation(summary = "新增角色接口")
    @PostMapping("/insertRole")
    public Result<Role> insertRole(@RequestBody Role role, HttpServletRequest request) {

        Result<Role> result = new Result<>();
        Boolean isAdmin = userService.isAdmin(request);
        if (isAdmin) {
            roleService.add(role);
            result.setResultSuccess("成功插入角色");
            result.setData(role);
        } else {
            result.setResultFailed("你不是管理员，不能插入角色");
        }
        return result;
    }

    /**
     * 插入角色
     *
     * @param request 请求，用于操作session
     * @return 结果对象
     */
    @Operation(summary = "查询角色接口")
    @GetMapping("/getAllRole")
    public Result<List<Role>> getAllRole(HttpServletRequest request) {

        Result<List<Role>> result = new Result<>();
        Boolean isAdmin = userService.isAdmin(request);
        if (isAdmin) {
            result.setResultSuccess("成功插入角色");
            result.setData(roleService.getAllRole());
        } else {
            result.setResultFailed("你不是管理员，不能插入角色");
        }
        return result;
    }
}
