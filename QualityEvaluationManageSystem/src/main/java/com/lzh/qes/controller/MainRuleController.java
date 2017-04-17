package com.lzh.qes.controller;

import com.lzh.qes.bean.MainRule;
import com.lzh.qes.modal.vo.MainRuleVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IMainRuleManageService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 细则大类控制层
 * Created by liuzhihao on 2017/4/17.
 */
@Controller
public class MainRuleController {
    private static Logger LOGGER = LoggerFactory.getLogger(MainRuleController.class);
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
}
