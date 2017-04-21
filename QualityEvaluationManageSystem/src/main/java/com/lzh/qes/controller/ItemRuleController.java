package com.lzh.qes.controller;

import com.lzh.qes.modal.vo.ItemRuleVO;
import com.lzh.qes.service.IItemRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzhihao on 2017/4/21.
 */
@Controller
public class ItemRuleController {
    private static Logger LOGGER = LoggerFactory.getLogger(ItemRuleController.class);
    @Autowired
    private IItemRuleService iItemRuleService;

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
        return iItemRuleService.findItemRuleByDetailRuleIdList(detailRuleId);
    }
}
