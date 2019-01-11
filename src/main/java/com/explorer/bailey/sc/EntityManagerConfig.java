package com.explorer.bailey.sc;

import com.explorer.bailey.sc.jpa.impl.DefaultRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/11
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.explorer.bailey.sc.dao"}, repositoryBaseClass = DefaultRepositoryImpl.class)
public class EntityManagerConfig {

}
