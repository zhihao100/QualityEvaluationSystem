package com.lzh.qes.controller;

import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.modal.vo.DetailRuleVO;
import com.lzh.qes.service.IDetailRuleService;
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
 * Created by liuzhihao on 2017/4/20.
 */
@Controller
public class DetailRuleController {
    private static Logger LOGGER = LoggerFactory.getLogger(DetailRuleController.class);
    @Autowired
    private IDetailRuleService iDetailRuleService;

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
        Page<DetailRule> detailRules = iDetailRuleService.findDetailRuleByMultiConditionAndPage(pageUtils);
        LOGGER.info("细则数据条数" + detailRules.getTotalElements());
        return detailRules;
    }

    /**
     * 修改细则状态
     *
     * @param detailRule
     * @return
     */
    @RequestMapping(value = "updateDetailRuleState", method = RequestMethod.POST)
    @ResponseBody
    public String updateDetailRuleState(@RequestBody DetailRule detailRule) {
        LOGGER.info("修改细则状态");
        Assert.notNull(detailRule, "细则为空");
        return iDetailRuleService.updateDetailRuleState(detailRule);
    }

    /**
     * 新增细则
     *
     * @param detailRule
     * @return
     */
    @RequestMapping(value = "createDetailRule", method = RequestMethod.POST)
    @ResponseBody
    public String createDetailRule(@RequestBody DetailRule detailRule) {
        LOGGER.info("新增细则");
        Assert.notNull(detailRule, "细则为空");
        return iDetailRuleService.createDetailRule(detailRule);
    }

    /**
     * 细则详情
     *
     * @param ruleId
     * @return
     */
    @RequestMapping(value = "showDetailRuleInfo", method = RequestMethod.POST)
    @ResponseBody
    public DetailRuleVO showDetailRuleInfo(@RequestBody Integer ruleId) {
        LOGGER.info("细则详情");
        Assert.notNull(ruleId, "细则不存在");
        return iDetailRuleService.showDetailRuleInfo(ruleId);
    }

    /**
     * 修改细则
     *
     * @param detailRule
     * @return
     */
    @RequestMapping(value = "updateDetailRule", method = RequestMethod.POST)
    @ResponseBody
    public String updateDetailRule(@RequestBody DetailRule detailRule) {
        LOGGER.info("修改细则");
        Assert.notNull(detailRule, "细则为空");
        return iDetailRuleService.updateDetailRule(detailRule);
    }

}
