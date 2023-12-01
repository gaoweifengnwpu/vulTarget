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
@Schema(description = "菜单model")
public class Menu implements Serializable {


    private Integer id;

    /**
     * 菜单名
     */
    @Schema(description = "菜单名")
    private String menuName;

    /**
     * 菜单描述
     */
    @Schema(description = "菜单描述")
    private String menuDescription;

}