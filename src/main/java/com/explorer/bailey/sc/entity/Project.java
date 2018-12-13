package com.explorer.bailey.sc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer", "signInfoSet" })
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

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "project")
    private Set<SignInfo> signInfoSet = new HashSet<>();

    public Project(Long creator, String name) {
        this.creator = creator;
        this.name = name;
    }

}
