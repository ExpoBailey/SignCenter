package com.explorer.bailey.sc.dao;

import com.explorer.bailey.sc.entity.AwardRecord;
import com.explorer.bailey.sc.jpa.DefaultRepository;
import com.explorer.bailey.sc.model.AwardModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
public interface IAwardRecordDao extends DefaultRepository<AwardRecord, Long> {

    @Query(value = "select new com.explorer.bailey.sc.model.AwardModel(" +
            "   a.id, a.type, a.name, a.status, a.probable, count(a.id)" +
            "   ) from AwardRecord ar, com.explorer.bailey.sc.entity.Award a " +
            "       where a.id = ar.award.id and ar.user.id = :userId" )
    List<AwardModel> countAwardRecord(Long userId);

    @Query(value = "select new com.explorer.bailey.sc.model.AwardModel(" +
            "   a.id, a.type, a.name, a.status, a.probable, count(a.id)" +
            "   ) from AwardRecord ar, com.explorer.bailey.sc.entity.Award a " +
            "       where a.id = ar.award.id and ar.award.id = :awardId and ar.user.id = :userId" )
    List<AwardModel> countAwardRecordByAward(Long userId, Long awardId);
}
