package com.explorer.bailey.sc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
@Entity
@Table(name = "SC_EFFORT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Effort implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int status;

    enum Status {
        BAN(0),
        USE(1);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
