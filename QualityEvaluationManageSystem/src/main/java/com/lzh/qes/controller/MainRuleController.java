package com.lzh.qes.controller;

import com.lzh.qes.bean.MainRule;
import com.lzh.qes.modal.vo.MainRuleVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IMainRuleManageService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 细则大类控制层
 * Created by liuzhihao on 2017/4/17.
 */
@Controller
public class MainRuleController {
    private static Logger LOGGER = LoggerFactory.getLogger(MainRuleController.class);
    @Autowired
    private IMainRuleManageService iMainRuleManageService;

    /**
     * 多条件分页查询细则大类
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllMainRuleByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public PageList<MainRuleVO> findAllMainRuleByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询细则大类");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getMainRule()) {
            MainRule mainRule = new MainRule();
            pageUtils.setMainRule(mainRule);
        }
        PageList<MainRuleVO> mainRules = iMainRuleManageService.findAllMainRuleByMultiConditionAndPage(pageUtils);
        LOGGER.info("细则大类数据条数" + mainRules.getPagersInfo().get("totalElements"));
        return mainRules;
    }

    /**
     * 修改细则大类状态
     *
     * @param mainRule
     * @return
     */
    @RequestMapping(value = "updateMainRuleState", method = RequestMethod.POST)
    @ResponseBody
    public String updateMainRuleState(@RequestBody MainRule mainRule) {
        LOGGER.info("修改细则大类状态");
        Assert.notNull(mainRule, "细则大类为空");
        return iMainRuleManageService.updateMainRuleState(mainRule);
    }

    /**
     * 细则大类详情
     *
     * @param ruleId
     * @return
     */
    @RequestMapping(value = "showMainRuleDetails", method = RequestMethod.POST)
    @ResponseBody
    public MainRuleVO showMainRuleDetails(@RequestBody Integer ruleId) {
        LOGGER.info("细则大类详情");
        Assert.notNull(ruleId);
        return iMainRuleManageService.showMainRuleDetails(ruleId);
    }

    /**
     * 添加细则大类
     *
     * @param mainRule
     * @return
     */
    @RequestMapping(value = "createMainRule", method = RequestMethod.POST)
    @ResponseBody
    public String createMainRule(@RequestBody MainRule mainRule) {
        LOGGER.info("添加细则大类");
        Assert.notNull(mainRule, "细则大类为空");
        return iMainRuleManageService.createMainRule(mainRule);
    }

    /**
     * 修改细则大类
     *
     * @param mainRule
     * @return
     */
    @RequestMapping(value = "updateMainRule", method = RequestMethod.POST)
    @ResponseBody
    public String updateMainRule(@RequestBody MainRule mainRule) {
        LOGGER.info("修改细则大类");
        Assert.notNull(mainRule, "细则大类为空");
        return iMainRuleManageService.updateMainRule(mainRule);
    }

    /**
     * 根据instituteId查询细则大类详情
     *
     * @param instituteId
     * @return
     */
    @RequestMapping(value = "showMainRuleDetailsByInstituteId", method = RequestMethod.POST)
    @ResponseBody
    public List<MainRule> showMainRuleDetailsByInstituteId(@RequestBody Integer instituteId) {
        LOGGER.info("根据instituteId细则大类详情");
        Assert.notNull(instituteId, "学院不存在");
        return iMainRuleManageService.showMainRuleDetailsByInstituteId(instituteId);
    }
}
