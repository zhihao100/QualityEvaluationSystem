package com.lzh.qes.service;


import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.modal.vo.DetailRuleVO;
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

    /**
     * 添加某大类的细则
     *
     * @param detailRule
     */
    String createDetailRule(DetailRule detailRule);

    /**
     * 修改细则状态
     *
     * @param detailRule
     * @return
     */
    String updateDetailRuleState(DetailRule detailRule);

    /**
     * 通过细则ID查询细则详情
     *
     * @param ruleId
     * @return
     */
    DetailRuleVO showDetailRuleInfo(Integer ruleId);

    /**
     * 修改细则
     *
     * @param detailRule
     * @return
     */
    String updateDetailRule(DetailRule detailRule);
}

