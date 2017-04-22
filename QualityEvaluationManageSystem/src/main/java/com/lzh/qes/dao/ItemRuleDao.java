package com.lzh.qes.dao;

import com.lzh.qes.bean.ItemRule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/21.
 */
public interface ItemRuleDao extends CrudRepository<ItemRule, Integer> {
    /**
     * 根据细则ID查找该细则所有项目
     */
    List<ItemRule> findByDetailRuleId(Integer detailRuleId);

    /**
     * 根据项目ID查询项目详情
     *
     * @param itemId
     * @return
     */
    ItemRule findByItemId(Integer itemId);

    /**
     * 根据项目所属细则ID和项目名查询项目详情
     *
     * @param detailRuleId
     * @param itemName
     * @return
     */
    ItemRule findByDetailRuleIdAndItemName(Integer detailRuleId, String itemName);
}
