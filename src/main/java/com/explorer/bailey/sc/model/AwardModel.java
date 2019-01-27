package com.explorer.bailey.sc.model;

import lombok.*;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AwardModel {

    private Long id;

    private Integer type;

    private String name;

    private Integer status;

    private double probable;

    private int sort;

    private long num;

    private String path;

    private String color;

    public AwardModel(Long id, String name, Long num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    public AwardModel(Long id, int type, String name, int status, double probable, long num, String path, String color) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.probable = probable;
        this.name = name;
        this.num = num;
        this.path = path;
        this.color = color;
    }

}
