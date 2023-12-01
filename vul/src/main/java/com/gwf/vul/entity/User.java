package com.gwf.vul.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用户类
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"password", "id"}, allowSetters = true)
@Schema(description = "用户model")
public class User implements Serializable {

    /**
     * 用户id
     */

    private Integer id;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;
    // 多对多的关系映射，一个用户可以有多个角色
    private List<Role> roles;


}