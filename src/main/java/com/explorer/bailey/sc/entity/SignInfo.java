package com.explorer.bailey.sc.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SC_SIGN_INFO")
public class SignInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "SC_SIGN_INFO_USER_ID"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "SC_SIGN_INFO_PROJECT_ID"))
    private Project project;

    private Date startDate;

    private Date endDate;

    private String remark;

    /**
     * 0:正常卡；1：补卡
     */
    private int status;

}
