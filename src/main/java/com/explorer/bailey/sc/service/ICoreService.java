package com.explorer.bailey.sc.service;

import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.entity.Project;
import com.explorer.bailey.sc.entity.SignInfo;

import java.util.Date;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
public interface ICoreService {

    boolean login(String userCode, String password);

    boolean out();

    boolean existProject(Long userId, String projectName);

    boolean saveProject(Project project);

    boolean deleteProject(Long projectId);

    Project findProjectById(Long id);

    List<Project> findAllByCreator(Long userId);

    boolean sign(Long id, Long projectId, Date startDate, Date endDate, String remark);

    List<SignInfo> findSignInfo(Long userId, List<Long> projectIds, Date startDate, Date endDate, WebConstant.Sort sort);

    SignInfo findSingInfoById(Long signInfoId);
}
