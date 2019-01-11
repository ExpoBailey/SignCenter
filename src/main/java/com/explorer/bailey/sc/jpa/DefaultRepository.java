package com.explorer.bailey.sc.jpa;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/11
 */
@NoRepositoryBean
public interface DefaultRepository<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

    List<E> list(DetachedCriteria detachedCriteria);
}
