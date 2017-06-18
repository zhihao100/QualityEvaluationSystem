package com.lzh.qes.controller;

import com.lzh.qes.bean.MainRule;
import com.lzh.qes.bean.Student;
import com.lzh.qes.service.IIndexService;
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
 * Created by liuzhihao on 2017/5/15.
 */
@Controller
public class IndexController {
    private static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private IIndexService iIndexService;

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @RequestMapping(value = "getCurrentUser", method = RequestMethod.POST)
    @ResponseBody
    public Student getCurrentUser() {
        LOGGER.info("获取当前登录用户");
        Student currentUser = iIndexService.findUser();
        currentUser.setStudentPassword("");
        return currentUser;
    }

    /**
     * 多条件分页查询细则大类
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllMainRuleByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public Page<MainRule> findAllMainRuleByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询细则大类");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getMainRule()) {
            MainRule mainRule = new MainRule();
            pageUtils.setMainRule(mainRule);
        }
        Page<MainRule> mainRules = iIndexService.findAllMainRuleByMultiConditionAndPage(pageUtils);
        LOGGER.info("细则大类数据条数" + mainRules.getTotalElements());
        return mainRules;
    }
}
