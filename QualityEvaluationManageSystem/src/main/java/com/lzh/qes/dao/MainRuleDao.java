package com.lzh.qes.dao;

import com.lzh.qes.bean.MainRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/17.
 */
public interface MainRuleDao extends CrudRepository<MainRule, Integer> {
    /**
     * 多条件分页查询细则大类
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<MainRule> findAll(Specification<MainRule> spec, Pageable pageable);

    /**
     * 通过ruleId查询该类详情
     *
     * @param ruleId
     * @return
     */
    MainRule findMainRuleByRuleId(Integer ruleId);

    /**
     * 通过ruleName和查询instituteId该类详情
     *
     * @param ruleName,instituteId
     * @return
     */
    MainRule findMainRuleByRuleNameAndInstituteId(String ruleName, Integer instituteId);

    /**
     * 根据instituteId查询细则大类详情
     *
     * @param instituteId
     * @return
     */
    List<MainRule> findMainRuleByInstituteId(Integer instituteId);
}
