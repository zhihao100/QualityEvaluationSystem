package com.lzh.qes.service;


import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.utils.PageUtils;
import org.springframework.data.domain.Page;

public interface IDetailRuleService {
    /**
     * 多条件分页查询某学院某大类的细则
     *
     * @param pageUtils
     * @return
     */
    Page<DetailRule> findDetailRuleByMultiConditionAndPage(PageUtils pageUtils);
}

