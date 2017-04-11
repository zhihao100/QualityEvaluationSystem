package com.lzh.qes.controller;

import com.lzh.qes.bean.Institute;
import com.lzh.qes.service.IInstituteManageService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzhihao on 2017/4/11.
 */
@Controller
public class InstituteManageController {
    private static Logger LOGGER = LoggerFactory.getLogger(InstituteManageController.class);
    @Autowired
    private IInstituteManageService instituteManageService;

    /**
     * 添加学院
     *
     * @param institute
     * @return
     */
    @RequestMapping(value = "createInstitute", method = RequestMethod.POST)
    @ResponseBody
    public String createInstitute(@RequestBody Institute institute) {
        LOGGER.info("添加学院");
        Assert.notNull(institute);
        return instituteManageService.createInstitute(institute);
    }

    /**
     * 多条件分页查询学院
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllInstituteByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public Page<Institute> findAllInstituteByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询学院");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getInstitute()) {
            Institute institute = new Institute();
            pageUtils.setInstitute(institute);
        }
        Page<Institute> institutes = instituteManageService.findAllInstituteByMultiConditionAndPage(pageUtils);
        LOGGER.info("学院数据条数" + institutes.getContent().size());
        return institutes;
    }

    /**
     * 修改学院状态
     *
     * @param institute
     * @return
     */
    @RequestMapping(value = "updateInstituteState", method = RequestMethod.POST)
    @ResponseBody
    public String updateInstituteState(@RequestBody Institute institute) {
        LOGGER.info("修改学院状态");
        Assert.notNull(institute, "学院为空");
        return instituteManageService.updateInstituteState(institute);
    }

    /**
     * 学院详情
     *
     * @param instituteId
     * @return
     */
    @RequestMapping(value = "showInstituteDetails", method = RequestMethod.POST)
    @ResponseBody
    public Institute showInstituteDetails(@RequestBody Integer instituteId) {
        LOGGER.info("学院详情");
        Assert.notNull(instituteId);
        return instituteManageService.showInstituteDetails(instituteId);
    }

    /**
     * 修改学院
     *
     * @param institute
     * @return
     */
    @RequestMapping(value = "updateInstitute", method = RequestMethod.POST)
    @ResponseBody
    public String updateInstitute(@RequestBody Institute institute) {
        LOGGER.info("修改学院");
        Assert.notNull(institute);
        return instituteManageService.updateInstitute(institute);
    }

}
