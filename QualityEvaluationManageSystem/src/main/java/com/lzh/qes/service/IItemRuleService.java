package com.lzh.qes.service;

import com.lzh.qes.modal.vo.ItemRuleVO;

/**
 * Created by liuzhihao on 2017/4/21.
 */
public interface IItemRuleService {
    /**
     * 根据细则ID查找该细则所有项目
     */
    ItemRuleVO findItemRuleByDetailRuleIdList(Integer detailRuleId);
}
