package com.explorer.bailey.sc.service.impl;

import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.entity.SignInfo;
import com.explorer.bailey.sc.service.ICoreService;
import com.explorer.bailey.sc.service.ICountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/25
 */
@Service("countService")
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class CountServiceImpl implements ICountService {

    @Resource
    private ICoreService coreService;

    @Override
    public List<String> findDateList(Long userId, List<Long> projectIds) {

        List<SignInfo> signInfoList = coreService.findSignInfo(userId, projectIds, null, null, WebConstant.Sort.ASC);

        Set<String> dateSet = new HashSet<>();

        signInfoList.forEach( signInfo -> {

            dateSet.add(signInfo.getStartDate().toString().split(" ")[0]);

        });

        return Arrays.asList(dateSet.toArray(new String[0]));
    }
}
