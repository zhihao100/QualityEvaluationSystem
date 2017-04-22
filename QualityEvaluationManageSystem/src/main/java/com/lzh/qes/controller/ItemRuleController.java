package com.lzh.qes.controller;

import com.lzh.qes.bean.ItemRule;
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

    /**
     * 更改项目状态
     *
     * @param itemRule
     * @return
     */
    @RequestMapping(value = "updateItemRuleState", method = RequestMethod.POST)
    @ResponseBody
    public String updateItemRuleState(@RequestBody ItemRule itemRule) {
        LOGGER.info("更改项目状态");
        Assert.notNull(itemRule, "该项目不存在");
        return iItemRuleService.updateItemRuleState(itemRule);
    }

    /**
     * 新增项目
     *
     * @param itemRule
     * @return
     */
    @RequestMapping(value = "itemRuleAdd", method = RequestMethod.POST)
    @ResponseBody
    public String itemRuleAdd(@RequestBody ItemRule itemRule) {
        LOGGER.info("新增项目");
        Assert.notNull(itemRule, "该项目为空");
        return iItemRuleService.itemRuleAdd(itemRule);
    }

    /**
     * 编辑项目
     *
     * @param itemRule
     * @return
     */
    @RequestMapping(value = "itemRuleEdit", method = RequestMethod.POST)
    @ResponseBody
    public String itemRuleEdit(@RequestBody ItemRule itemRule) {
        LOGGER.info("编辑项目");
        Assert.notNull(itemRule, "该项目为空");
        return iItemRuleService.itemRuleEdit(itemRule);
    }
}
