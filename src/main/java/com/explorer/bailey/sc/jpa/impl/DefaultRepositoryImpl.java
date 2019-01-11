package com.explorer.bailey.sc.jpa.impl;

import com.explorer.bailey.sc.jpa.DefaultRepository;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/11
 */
@SuppressWarnings("unchecked")
@NoRepositoryBean
public class DefaultRepositoryImpl<E, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements DefaultRepository<E, ID> {

    protected EntityManager entityManager;

    public DefaultRepositoryImpl(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public DefaultRepositoryImpl(Class<E> domainClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager);
    }

    @Override
    public List<E> list(DetachedCriteria detachedCriteria) {
        return detachedCriteria.getExecutableCriteria((Session) entityManager.getDelegate()).list();
    }
}
