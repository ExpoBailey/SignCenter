package com.explorer.bailey.sc.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
public class SignModel {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @NotNull(message = "开始时间不能为空")
    private Date startDate;

    @NotNull(message = "结束时间不能为空")
    private Date endDate;
}
