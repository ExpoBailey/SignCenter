package com.explorer.bailey.sc.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SC_PROJECT")
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer projectType;

    private String name;

    @CreatedBy
    private Long creator;

    @CreatedDate
    private Date createDate;

    @LastModifiedBy
    private Long modifier;

    @LastModifiedDate
    private Date modifyDate;

    public Project(Long creator, String name) {
        this.creator = creator;
        this.name = name;
    }

}
