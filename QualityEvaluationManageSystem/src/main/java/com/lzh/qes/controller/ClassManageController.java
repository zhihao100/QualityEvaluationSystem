package com.lzh.qes.controller;


import com.lzh.qes.bean.ClassManage;
import com.lzh.qes.service.IClassManageService;
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

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/12.
 */
@Controller
public class ClassManageController {
    private static Logger LOGGER = LoggerFactory.getLogger(ClassManageController.class);
    @Autowired
    private IClassManageService iclassManageService;

    /**
     * 添加班级
     *
     * @param class
     * @return
     */
    @RequestMapping(value = "createClass", method = RequestMethod.POST)
    @ResponseBody
    public String createClass(@RequestBody Class class) {
        LOGGER.info("添加班级");
        Assert.notNull( class);
        return iclassManageService.createClass( class);
    }

    /**
     * 多条件分页查询班级
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllClassByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public Page<ClassManage> findAllClassByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询班级");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getClassManage()) {
            ClassManage class =new ClassManage();
            pageUtils.setClassManage( class);
        }
        Page<ClassManage> classs = iclassManageService.findAllClassByMultiConditionAndPage(pageUtils);
        LOGGER.info("班级数据条数" + classs.getContent().size());
        return classs;
    }

    /**
     * 查找所有班级
     */
    @RequestMapping(value = "findAllClass", method = RequestMethod.POST)
    @ResponseBody
    public List<ClassManage> findAllClass() {
        LOGGER.info("查询所有班级");
        return iclassManageService.findAllClass();
    }

    /**
     * 修改班级状态
     *
     * @param class
     * @return
     */
    @RequestMapping(value = "updateClassState", method = RequestMethod.POST)
    @ResponseBody
    public String updateClassState(@RequestBody Class class) {
        LOGGER.info("修改班级状态");
        Assert.notNull( class,"班级为空");
        return iclassManageService.updateClassState( class);
    }

    /**
     * 班级详情
     *
     * @param classId
     * @return
     */
    @RequestMapping(value = "showClassDetails", method = RequestMethod.POST)
    @ResponseBody
    public ClassManage showClassDetails(@RequestBody Integer classId) {
        LOGGER.info("班级详情");
        Assert.notNull(classId);
        return iclassManageService.showClassDetails(classId);
    }

    /**
     * 修改班级
     *
     * @param class
     * @return
     */
    @RequestMapping(value = "updateClass", method = RequestMethod.POST)
    @ResponseBody
    public String updateClass(@RequestBody Class class) {
        LOGGER.info("修改班级");
        Assert.notNull( class);
        return iclassManageService.updateClass( class);
    }

}
