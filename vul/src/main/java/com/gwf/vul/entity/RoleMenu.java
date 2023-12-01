package com.gwf.vul.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 菜单类
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "角色菜单model")
public class RoleMenu implements Serializable {


    private Integer roleId;
    private Integer menuId;
}