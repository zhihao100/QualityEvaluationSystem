package com.lzh.qes.service.impl;

import com.lzh.qes.bean.ItemRule;
import com.lzh.qes.dao.ItemRuleDao;
import com.lzh.qes.modal.vo.ItemRuleVO;
import com.lzh.qes.service.IDetailRuleService;
import com.lzh.qes.service.IItemRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/21.
 */
@Service
public class ItemRuleService implements IItemRuleService {
    @Autowired
    private ItemRuleDao itemRuleDao;
    @Autowired
    private IDetailRuleService iDetailRuleService;

    /**
     * 根据细则ID查找该细则所有项目
     */
    @Override
    public ItemRuleVO findItemRuleByDetailRuleIdList(Integer detailRuleId) {
        List<ItemRule> itemRuleList = itemRuleDao.findByDetailRuleId(detailRuleId);
        ItemRuleVO itemRuleVO = new ItemRuleVO();
        itemRuleVO.setDetailRuleName(iDetailRuleService.showDetailRuleInfo(detailRuleId).getDetailRule().getRuleName());
        itemRuleVO.setMainRule(iDetailRuleService.showDetailRuleInfo(detailRuleId).getMainRule());
        itemRuleVO.setItemRuleList(itemRuleList);
        return itemRuleVO;
    }

    /**
     * 更改项目状态
     *
     * @param itemRule
     * @return
     */
    @Override
    public String updateItemRuleState(ItemRule itemRule) {
        if (null != itemRule.getItemId()) {
            ItemRule itemRuleResult = itemRuleDao.findByItemId(itemRule.getItemId());
            itemRuleResult.setItemState(itemRule.getItemState());
            itemRuleDao.save(itemRuleResult);
            return "修改完成";
        }
        return "修改失败，该项目不存在";
    }

    /**
     * 新增项目
     *
     * @param itemRule
     * @return
     */
    @Override
    public String itemRuleAdd(ItemRule itemRule) {
        ItemRule itemRuleResult = itemRuleDao.findByDetailRuleIdAndItemName(itemRule.getDetailRuleId(), itemRule.getItemName());
        if (null == itemRuleResult) {
            itemRuleDao.save(itemRule);
            return "新增完成";
        }
        return "该项目已存在";
    }

    /**
     * 编辑项目
     *
     * @param itemRule
     * @return
     */
    @Override
    public String itemRuleEdit(ItemRule itemRule) {
        if (null == itemRule.getItemId()) {
            return "该项目不存在";
        }
        ItemRule existedItemRule = itemRuleDao.findByItemId(itemRule.getItemId());
        ItemRule existedItemRuleName = itemRuleDao.findByDetailRuleIdAndItemName(itemRule.getDetailRuleId(), itemRule.getItemName());
        if (!existedItemRule.getItemName().equals(itemRule.getItemName()) && null != existedItemRuleName) {
            return "该项目已存在";
        }
        existedItemRule.setItemName(itemRule.getItemName());
        existedItemRule.setItemScore(itemRule.getItemScore());
        existedItemRule.setItemState(itemRule.getItemState());
        itemRuleDao.save(existedItemRule);
        return "修改完成";
    }
}
