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
}
