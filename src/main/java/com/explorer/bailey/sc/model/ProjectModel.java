package com.explorer.bailey.sc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
public class ProjectModel {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "项目类型不能为空")
    private Integer projectType;

    @NotEmpty(message = "项目名不能为空")
    private String name;
}
