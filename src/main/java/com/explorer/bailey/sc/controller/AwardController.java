package com.explorer.bailey.sc.controller;

import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.entity.Award;
import com.explorer.bailey.sc.model.AwardModel;
import com.explorer.bailey.sc.service.IAwardService;
import com.explorer.bailey.sc.utils.PageUtils;
import com.minstone.mobile.core.common.entity.Pager;
import com.minstone.mobile.core.spring.api.ApiResult;
import com.minstone.mobile.core.spring.validation.ObjectValid;
import com.minstone.mobile.core.spring.validation.ValidStarter;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
@RestController
@RequestMapping("/api/award")
public class AwardController {

    @Resource
    private IAwardService awardService;

    @PostMapping("/add")
    @ValidStarter
    public ApiResult add(@ObjectValid(fieldNames = {"type","name","status","probable"})
                         @RequestBody AwardModel awardModel) {
        Award award = new Award();
        award.setName(awardModel.getName());
        award.setType(awardModel.getType());
        award.setProbable(awardModel.getProbable());
        award.setPath(awardModel.getPath());
        award.setColor(awardModel.getColor());
        award.setStatus(awardModel.getStatus());
        boolean success = awardService.save(award);
        return ApiResult.asserts(success);
    }

    @PostMapping("/update")
    @ValidStarter
    public ApiResult update(@ObjectValid(fieldNames = {"id","type","name","status","probable"})
                            @RequestBody AwardModel awardModel) {
        boolean success = awardService.save(new Award(
                                                    awardModel.getId(),
                                                    awardModel.getType(),
                                                    awardModel.getName(),
                                                    awardModel.getStatus(),
                                                    awardModel.getProbable(),
                                                    awardModel.getPath(),
                                                    awardModel.getColor()));
        return ApiResult.asserts(success);
    }

    @GetMapping("/get")
    public ApiResult findAward(AwardModel awardModel) {
        Award award = awardService.findAwardById(awardModel.getId());
        return ApiResult.asserts(award);
    }

    @PostMapping("/status")
    @ValidStarter
    public ApiResult delete(@ObjectValid(fieldNames = {"id","status"})
                            @RequestBody AwardModel awardModel) {
        boolean success = awardService.updateAwardStatusById(
                                            awardModel.getId(),
                                            awardModel.getStatus() == Award.Status.BAN.getValue() ? Award.Status.BAN : Award.Status.USE);
        return ApiResult.asserts(success);
    }

    @GetMapping("/page")
    public ApiResult findPageAward(AwardModel awardModel, Pager pager) {
        Page<Award> page = awardService.findPage(
                                                awardModel.getType(),
                                                awardModel.getName(),
                                                awardModel.getStatus(),
                                                awardModel.getSort() == WebConstant.Sort.DESC.getValue() ? WebConstant.Sort.DESC : WebConstant.Sort.ASC,
                                                pager.getPageIndex() == 0 ? 1 : pager.getPageIndex(),
                                                pager.getPageSize() == 0 ? 10 : pager.getPageSize());
        return ApiResult.asserts(PageUtils.pageToApiPager(page));
    }

}
