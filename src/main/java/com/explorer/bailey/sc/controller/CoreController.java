package com.explorer.bailey.sc.controller;

import com.explorer.bailey.sc.common.WebConstant;
import com.explorer.bailey.sc.entity.Project;
import com.explorer.bailey.sc.entity.SignInfo;
import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.model.ProjectModel;
import com.explorer.bailey.sc.model.SignModel;
import com.explorer.bailey.sc.model.UserModel;
import com.explorer.bailey.sc.service.ICoreService;
import com.explorer.bailey.sc.service.IUserService;
import com.explorer.bailey.sc.utils.SessionUtils;
import com.minstone.mobile.core.common.utils.judge.JudgeUtils;
import com.minstone.mobile.core.spring.api.ApiResult;
import com.minstone.mobile.core.spring.validation.ObjectValid;
import com.minstone.mobile.core.spring.validation.ValidStarter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/8
 */
@RestController
@RequestMapping("/api")
public class CoreController {

    @Resource
    private IUserService userService;

    @Resource
    private ICoreService coreService;

    @PostMapping("/user/add")
    @ValidStarter
    public ApiResult addUser(@ObjectValid(fieldNames = {"userCode", "userName", "password"}) @RequestBody UserModel userModel) {
        User user = new User();
        user.setUserCode(userModel.getUserCode());
        user.setUserName(userModel.getUserName());
        user.setPassword(userModel.getPassword());
        boolean success = userService.saveUser(user);
        return ApiResult.asserts(success);
    }

    @PostMapping("/user/update")
    @ValidStarter
    public ApiResult updateUser(@ObjectValid(fieldNames = {"userCode", "userName", "password"}) @RequestBody UserModel userModel) {
        User userDb = userService.getUserByUserCode(userModel.getUserCode());
        User user = new User();
        user.setId(userDb.getId());
        user.setUserName(userModel.getUserName());
        user.setPassword(userModel.getPassword());
        boolean success = userService.saveUser(user);
        return ApiResult.asserts(success);
    }

    @GetMapping("/user/get")
    public ApiResult getUserByUserCode(User user) {
        user = userService.getUserByUserCode(user.getUserCode());
        user.setPassword(null);
        return ApiResult.asserts(user);
    }

    @PostMapping("/login/into")
    @ValidStarter
    public ApiResult loginInto(@ObjectValid(fieldNames = {"userCode", "password"}) @RequestBody UserModel userModel) {
        boolean success = coreService.login(userModel.getUserCode(), userModel.getPassword());
        return ApiResult.asserts(success);
    }

    @GetMapping("/login/out")
    public ApiResult loginOut() {
        boolean success = coreService.out();
        return ApiResult.asserts(success);
    }

    @PostMapping("/project/exist")
    @ValidStarter
    public ApiResult existProject(@ObjectValid(fieldNames = {"name"}) @RequestBody ProjectModel projectModel) {
        boolean exist = coreService.existProject(SessionUtils.getUser().getId(), projectModel.getName());
        HashMap<String, Object> data = new HashMap<>(1);
        data.put("exist", exist);
        return ApiResult.asserts(data);
    }

    @PostMapping("/project/add")
    @ValidStarter
    public ApiResult addProject(@ObjectValid(fieldNames = {"projectType", "name"}) @RequestBody ProjectModel projectModel) {
        Project project = new Project();
        project.setProjectType(projectModel.getProjectType());
        project.setName(projectModel.getName());
        boolean success = coreService.saveProject(project);
        return ApiResult.asserts(success);
    }

    @PostMapping("/project/update")
    @ValidStarter
    public ApiResult updateProject(@ObjectValid(fieldNames = {"id", "projectType", "name"}) @RequestBody ProjectModel projectModel) {
        Project project = new Project();
        project.setId(projectModel.getId());
        project.setProjectType(projectModel.getProjectType());
        project.setName(projectModel.getName());
        boolean success = coreService.saveProject(project);
        return ApiResult.asserts(success);
    }

    @GetMapping("/project/delete")
    @ValidStarter
    public ApiResult deleteProject(@ObjectValid(fieldNames = {"id"})ProjectModel projectModel) {
        boolean success = coreService.deleteProject(projectModel.getId());
        return ApiResult.asserts(success);
    }

    @GetMapping("/project/get")
    @ValidStarter
    public ApiResult getProject(@ObjectValid(message = "项目id不能为空") Long id) {
        Project project = coreService.findProjectById(id);
        return ApiResult.asserts(project == null ? Collections.EMPTY_MAP : project);
    }

    @GetMapping("/project/all")
    public ApiResult findAllProject() {
        List<Project> list = coreService.findAllByCreator(SessionUtils.getUser().getId());
        return ApiResult.asserts(list);
    }

    @PostMapping("/core/sign")
    @ValidStarter
    public ApiResult sign(@ObjectValid(fieldNames = {"projectId", "startDate"}) @RequestBody SignModel signModel) {
        boolean success = coreService.sign(signModel.getId(), signModel.getProjectId(), signModel.getStartDate(), signModel.getEndDate(), signModel.getRemark());
        return ApiResult.asserts(success);
    }

    @GetMapping("/core/sign/all")
    public ApiResult findAllSignInfo(SignModel signModel) {
        List<SignInfo> list = coreService.findSignInfo(SessionUtils.getUser().getId(), signModel.getProjectId(), signModel.getStartDate(), signModel.getEndDate(), signModel.getSortFlag() == 0 ? WebConstant.Sort.DESC : WebConstant.Sort.ASC);
        if (!JudgeUtils.isEmpty(list)) {
            list.forEach( signInfo -> signInfo.setUser(null));
        }
        return ApiResult.asserts(list);
    }

}
