package com.gwf.vul.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色类
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "角色model")
public class Role implements Serializable {


    private Integer id;

    /**
     * 菜单名
     */
    @Schema(description = "菜单名")
    private String roleName;

    /**
     * 菜单描述
     */
    @Schema(description = "菜单描述")
    private String roleDescription;

}