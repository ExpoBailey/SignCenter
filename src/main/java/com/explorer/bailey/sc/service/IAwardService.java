package com.explorer.bailey.sc.service;

import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.entity.Award;
import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.model.AwardModel;
import com.explorer.bailey.sc.utils.SessionUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
public interface IAwardService {
    boolean save(Award award);

    Award findAwardById(Long id);

    boolean updateAwardStatusById(Long id, Award.Status ban);

    Page<Award> findPage(int type, String name, int status, WebConstant.Sort sort, int pageIndex, int pageSize);

    boolean addAwardRecord(User user, Award award);

    List<AwardModel> countAwardRecord(Long userId, Long awardId);

    Award lotto(Long userId);
}
