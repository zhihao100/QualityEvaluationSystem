package com.lzh.qes.service;

import com.lzh.qes.bean.ItemRule;
import com.lzh.qes.modal.vo.ItemRuleVO;

/**
 * Created by liuzhihao on 2017/4/21.
 */
public interface IItemRuleService {
    /**
     * 根据细则ID查找该细则所有项目
     */
    ItemRuleVO findItemRuleByDetailRuleIdList(Integer detailRuleId);

    /**
     * 更改项目状态
     *
     * @param itemRule
     * @return
     */
    String updateItemRuleState(ItemRule itemRule);

    /**
     * 新增项目
     *
     * @param itemRule
     * @return
     */
    String itemRuleAdd(ItemRule itemRule);

    /**
     * 编辑项目
     *
     * @param itemRule
     * @return
     */
    String itemRuleEdit(ItemRule itemRule);
}
