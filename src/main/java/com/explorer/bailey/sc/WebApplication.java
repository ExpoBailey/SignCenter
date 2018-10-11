package com.explorer.bailey.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/8
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
