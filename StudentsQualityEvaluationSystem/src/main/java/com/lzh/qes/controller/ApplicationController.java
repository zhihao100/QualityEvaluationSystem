package com.lzh.qes.controller;

import com.lzh.qes.bean.Application;
import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.modal.vo.ItemRuleVO;
import com.lzh.qes.service.IApplicationService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuzhihao on 2017/5/15.
 */
@Controller
public class ApplicationController {
    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    private IApplicationService iApplicationService;

    /**
     * 多条件分页查询某学院某大类的细则
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findDetailRuleByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public Page<DetailRule> findDetailRuleByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询细则");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getDetailRule()) {
            DetailRule detailRule = new DetailRule();
            pageUtils.setDetailRule(detailRule);
        }
        Page<DetailRule> detailRules = iApplicationService.findDetailRuleByMultiConditionAndPage(pageUtils);
        LOGGER.info("细则数据条数" + detailRules.getTotalElements());
        return detailRules;
    }

    /**
     * 根据细则ID查找该细则所有项目
     *
     * @param detailRuleId
     * @return
     */
    @RequestMapping(value = "itemRule", method = RequestMethod.POST)
    @ResponseBody
    public ItemRuleVO findItemRuleByDetailRuleIdList(@RequestBody Integer detailRuleId) {
        LOGGER.info("根据细则ID查找该细则所有项目");
        Assert.notNull(detailRuleId, "该细则不存在");
        return iApplicationService.findItemRuleByDetailRuleIdList(detailRuleId);
    }

    /**
     * 新增申请
     *
     * @param applications
     * @return
     */
    @RequestMapping(value = "applicationAdd", method = RequestMethod.POST)
    @ResponseBody
    public String applicationAdd(@RequestBody List<Application> applications) {
        LOGGER.info("新增申请");
        Assert.notNull(applications, "申请为空");
        return iApplicationService.applicationAdd(applications);
    }

    /**
     * 撤回申请
     *
     * @param studentId,itemId
     * @return
     */
    @RequestMapping(value = "applicationDelete/{studentId}", method = RequestMethod.POST)
    @ResponseBody
    public String applicationDelete(@PathVariable Long studentId, @RequestBody Integer itemId) {
        LOGGER.info("撤回申请");
        Assert.notNull(studentId, "该学生不存在");
        Assert.notNull(itemId, "该项目不存在");
        return iApplicationService.applicationDelete(studentId, itemId);
    }

    /**
     * 根据studentId,detailRuleId查找该学生已申请的项目
     *
     * @param studentId,detailRuleId
     * @return
     */
    @RequestMapping(value = "findApplicationByStudentIdAndDetailRuleId/{studentId}", method = RequestMethod.POST)
    @ResponseBody
    public List<Application> findApplicationByStudentIdAndDetailRuleIdList(@PathVariable Long studentId, @RequestBody Integer detailRuleId) {
        LOGGER.info("根据studentId,detailRuleId查找该学生已申请的项目");
        Assert.notNull(studentId, "该学生不存在");
        Assert.notNull(detailRuleId, "该细则不存在");
        return iApplicationService.findApplicationByStudentIdAndDetailRuleIdList(studentId, detailRuleId);
    }
}
