package com.explorer.bailey.sc.service.impl;

import com.explorer.bailey.sc.common.ServiceException;
import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.dao.IProjectDao;
import com.explorer.bailey.sc.dao.ISignInfoDao;
import com.explorer.bailey.sc.entity.Project;
import com.explorer.bailey.sc.entity.SignInfo;
import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.service.ICoreService;
import com.explorer.bailey.sc.service.IUserService;
import com.explorer.bailey.sc.utils.CookieUtils;
import com.explorer.bailey.sc.utils.SessionUtils;
import com.minstone.common.utils.StringUtils;
import com.minstone.common.utils.code.MD5CodeUtil;
import com.minstone.common.utils.date.DateUtils;
import com.minstone.mobile.core.common.utils.judge.JudgeUtils;
import com.minstone.mobile.core.spring.api.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Date;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
@Service("coreService")
@Slf4j
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class CoreServiceImpl implements ICoreService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectDao projectDao;

    @Autowired
    private ISignInfoDao signInfoDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean login(String userCode, String password) {
        User user = userService.getUserByUserCode(userCode);
        if (user == null) {
            throw new ServiceException(ApiStatus.NOT_FOUND, "该用户不存在");
        }
        if (StringUtils.isNotEmpty(password) && !user.getPassword().equals(MD5CodeUtil.md5(password))) {
            throw new ServiceException(ApiStatus.BAD_REQUEST, "密码不正确");
        }
        user.setPassword(null);
        SessionUtils.setAttributes(WebConstant.SESSION_USER, user);
        CookieUtils.addCookie(WebConstant.COOKIE_NAME, userCode, WebConstant.MAX_COOKIE, null);
        return true;
    }

    @Override
    public boolean out() {
        SessionUtils.removeAttributes(WebConstant.SESSION_USER);
        CookieUtils.removeCookie(WebConstant.COOKIE_NAME);
        return true;
    }

    @Override
    public boolean existProject(Long userId, String projectName) {
        Project project = new Project(userId, projectName);
        Example<Project> example = Example.of(project);
        return projectDao.exists(example);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveProject(Project project) {
        boolean exist = existProject(SessionUtils.getUser().getId(), project.getName());
        if (exist) {
            throw new ServiceException(ApiStatus.BAD_REQUEST, "该项目已存在，无法重复创建");
        }
        return projectDao.save(project) != null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteProject(Long projectId) {
        projectDao.delete(projectId);
        return true;
    }

    @Override
    public Project findProjectById(Long id) {
        return projectDao.findOne(id);
    }

    @Override
    public List<Project> findAllByCreator(Long userId) {
        return projectDao.findByCreator(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean sign(Long id, Long projectId, Date startDate, Date endDate, String remark) {
        Project project = projectDao.findOne(projectId);
        if (project == null) {
            throw new ServiceException(ApiStatus.NOT_FOUND, "该项目不存在，无法签到");
        }
        SignInfo signInfo = new SignInfo(id, SessionUtils.getUser(), project, startDate, endDate, remark);
        return signInfoDao.save(signInfo) != null;
    }

    @Override
    public List<SignInfo> findSignInfo(Long userId, List<Long> projectIds, Date startDate, Date endDate, WebConstant.Sort sort) {

        endDate = (endDate == null && startDate != null) ? DateUtils.getDateByStr("2099-12-12") : endDate;

        startDate = (startDate == null && endDate != null) ? DateUtils.getDateByStr("2018-01-01") : startDate;

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SignInfo.class);

        if (startDate != null) {
            detachedCriteria.add(Restrictions.between("startDate", startDate, endDate));
        }

        detachedCriteria.createAlias("user","u").add(Restrictions.eq("u.id", userId));

        detachedCriteria.createAlias("project","p");

        if (!JudgeUtils.isEmpty(projectIds)) {
            detachedCriteria.add(Restrictions.in("p.id", projectIds));
        }

        detachedCriteria.addOrder(sort == WebConstant.Sort.DESC ? Order.desc("startDate") : Order.asc("startDate"));

        return signInfoDao.list(detachedCriteria);

    }

    @Override
    public SignInfo findSingInfoById(Long signInfoId) {
        SignInfo signInfo = signInfoDao.findOne(signInfoId);
        System.out.println(signInfo);
        return signInfo;
    }
}
