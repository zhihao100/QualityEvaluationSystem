package com.lzh.qes.controller;

import com.lzh.qes.bean.Major;
import com.lzh.qes.enums.IsEnableState;
import com.lzh.qes.service.IMajorManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/13.
 */
@Controller
public class MajorController {
    private static Logger LOGGER = LoggerFactory.getLogger(MajorController.class);
    @Autowired
    private IMajorManageService iMajorManageService;

    /**
     * 根据学院ID查找该学院所有专业
     */
    @RequestMapping(value = "majorManage/{instituteId}", method = RequestMethod.POST)
    @ResponseBody
    public List<Major> findMajorByInstituteIdList(@PathVariable("instituteId") Integer instituteId) {
        LOGGER.info("查找该学院专业列表");
        Assert.notNull(instituteId);
        return iMajorManageService.findMajorByInstituteIdList(instituteId);
    }

    /**
     * 根据启用学院ID查找该学院所有启用专业
     */
    @RequestMapping(value = "enabledMajorManage/{instituteId}", method = RequestMethod.POST)
    @ResponseBody
    public List<Major> findEnabledMajorByInstituteIdList(@PathVariable("instituteId") Integer instituteId) {
        LOGGER.info("查找该启用学院启用专业列表");
        Assert.notNull(instituteId);
        return iMajorManageService.findMajorByInstituteIdAndMajorState(instituteId, IsEnableState.启用);
    }
    /**
     * 修改专业状态
     *
     * @param major
     * @return
     */
    @RequestMapping(value = "updateMajorState", method = RequestMethod.POST)
    @ResponseBody
    public String updateMajorState(@RequestBody Major major) {
        LOGGER.info("修改专业状态");
        Assert.notNull(major, "专业为空");
        return iMajorManageService.updateMajorState(major);
    }

    /**
     * 修改专业
     *
     * @param major
     * @return
     */
    @RequestMapping(value = "majorManageEdit", method = RequestMethod.POST)
    @ResponseBody
    public String editMajor(@RequestBody Major major) {
        LOGGER.info("修改专业");
        Assert.notNull(major, "专业为空");
        return iMajorManageService.editMajor(major);
    }

    /**
     * 新增专业
     *
     * @param major
     * @return
     */
    @RequestMapping(value = "majorManageAdd", method = RequestMethod.POST)
    @ResponseBody
    public String addMajor(@RequestBody Major major) {
        LOGGER.info("新增专业");
        Assert.notNull(major, "专业为空");
        return iMajorManageService.addMajor(major);
    }
}
