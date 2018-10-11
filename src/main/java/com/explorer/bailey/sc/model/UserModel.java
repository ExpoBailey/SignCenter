package com.explorer.bailey.sc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/10
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;

    @NotEmpty(message = "用户帐号不能为空")
    @Pattern(regexp = "[a-zA-Z]{3,5}", message = "帐号只有3-5位字母喔")
    private String userCode;

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
