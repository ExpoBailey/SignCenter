package com.explorer.bailey.sc.dao;

import com.explorer.bailey.sc.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
public interface IProjectDao extends JpaRepository<Project, Long> {
    List<Project> findByCreator(Long userId);
}
