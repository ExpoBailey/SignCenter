package com.explorer.bailey.sc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String remark;

    private int status;

    @JsonIgnore
    private int sortFlag;

    @JsonIgnore
    private List<Long> projectIds;
}
