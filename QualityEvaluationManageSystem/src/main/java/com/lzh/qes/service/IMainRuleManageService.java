package com.lzh.qes.service;

import com.lzh.qes.bean.MainRule;
import com.lzh.qes.modal.vo.MainRuleVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.utils.PageUtils;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/17.
 */
public interface IMainRuleManageService {
    /**
     * 多条件分页查询细则大类
     * @param pageUtils
     * @return
     */
    PageList<MainRuleVO> findAllMainRuleByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 修改细则大类状态
     *
     * @param mainRule
     * @return
     */
    String updateMainRuleState(MainRule mainRule);

    /**
     * 通过ruleId查询该类详情
     *
     * @param ruleId
     * @return
     */
    MainRuleVO showMainRuleDetails(Integer ruleId);

    /**
     * 新增细则大类
     *
     * @param mainRule
     * @return
     */
    String createMainRule(MainRule mainRule);

    /**
     * 修改细则大类状
     *
     * @param mainRule
     * @return
     */
    String updateMainRule(MainRule mainRule);

    /**
     * 根据instituteId查询细则大类详情
     *
     * @param instituteId
     * @return
     */
    List<MainRule> showMainRuleDetailsByInstituteId(Integer instituteId);
}
