package com.explorer.bailey.sc.service.impl;

import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.dao.IAwardDao;
import com.explorer.bailey.sc.dao.IAwardRecordDao;
import com.explorer.bailey.sc.entity.Award;
import com.explorer.bailey.sc.entity.AwardRecord;
import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.model.AwardModel;
import com.explorer.bailey.sc.service.IAwardService;
import com.explorer.bailey.sc.service.IUserService;
import com.explorer.bailey.sc.utils.LottoUtils;
import com.explorer.bailey.sc.utils.SessionUtils;
import com.minstone.common.utils.StringUtils;
import com.minstone.mobile.core.common.utils.judge.JudgeUtils;
import com.minstone.mobile.core.spring.api.ApiException;
import com.minstone.mobile.core.spring.api.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
@Service("awardService")
@Slf4j
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class AwardServiceImpl implements IAwardService {

    @Resource
    private IAwardDao awardDao;

    @Resource
    private IAwardRecordDao recordDao;

    @Resource
    private IUserService userService;

    @Override
    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public boolean save(Award award) {
        return awardDao.save(award) != null;
    }

    @Override
    public Award findAwardById(Long id) {
        return awardDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public boolean updateAwardStatusById(Long id, Award.Status ban) {

        Award award = awardDao.findOne(id);

        if (award == null) {
            throw new ApiException(ApiStatus.NOT_FOUND, "该奖品不存在");
        }

        award.setStatus(ban.getValue());

        return awardDao.save(award) != null;
    }

    @Override
    public Page<Award> findPage(Integer type, String name, Integer status, WebConstant.Sort sort, int pageIndex, int pageSize) {

        Specification<Award> specification = new Specification<Award>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                if (StringUtils.isNotEmpty(name)) {
                    list.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
                }

                if (type != null) {
                    list.add(cb.equal(root.get("type").as(Integer.class), type));
                }

                if (status != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), status));
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };

        PageRequest pageRequest = new PageRequest(pageIndex - 1, pageSize, new Sort(sort == WebConstant.Sort.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));

        return awardDao.findAll(specification, pageRequest);
    }

    @Override
    public boolean addAwardRecord(User user, Award award) {
        if (user == null || award == null) {
            throw new ApiException(ApiStatus.BAD_REQUEST, "用户、奖品实体不能为空");
        }
        AwardRecord awardRecord = new AwardRecord();
        awardRecord.setUser(user);
        awardRecord.setAward(award);
        return recordDao.save(awardRecord) != null;
    }

    @Override
    public List<AwardModel> countAwardRecord(Long userId, Long awardId) {
        if (awardId != null) {
            return recordDao.countAwardRecordByAward(userId, awardId);
        }
        return recordDao.countAwardRecord(userId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public Award lotto(Long userId) {

        // 选出奖品列表
        List<Award> awardList = awardDao.findByStatus(Award.Status.USE.getValue());

        if (JudgeUtils.isEmpty(awardList)) {
            return null;
        }

        double[] probables = new double[awardList.size()];

        for (int i = 0; i < awardList.size(); i++) {
            double probable = awardList.get(i).getProbable();
            probables[i] = probable < 0 ? 0 : probable;
        }

        int index = LottoUtils.probableModel(probables);

        Award award = awardList.get(index);

        // 将抽奖结果保存起来；
        AwardRecord awardRecord = new AwardRecord();
        awardRecord.setAward(award);
        awardRecord.setUser(userService.getUserById(userId));

        boolean insert = insert(awardRecord);
        log.info("保存抽奖纪录的结果：" + insert);
        return award;
    }

    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public boolean insert(AwardRecord awardRecord) {
        awardRecord.setId(null);
        return recordDao.save(awardRecord) != null;
    }

}
