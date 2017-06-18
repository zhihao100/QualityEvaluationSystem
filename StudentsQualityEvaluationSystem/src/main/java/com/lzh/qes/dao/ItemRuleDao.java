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

}
