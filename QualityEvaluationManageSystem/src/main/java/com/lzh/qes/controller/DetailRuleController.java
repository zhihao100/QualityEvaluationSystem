package com.lzh.qes.controller;

import com.lzh.qes.bean.DetailRule;
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
}
