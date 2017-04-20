package com.lzh.qes.service.impl;

import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.dao.DetailRuleDao;
import com.lzh.qes.service.IDetailRuleService;
import com.lzh.qes.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuzhihao on 2017/4/20.
 */
@Service
public class DetailRuleService implements IDetailRuleService {
    @Autowired
    private DetailRuleDao detailRuleDao;

    @Override
    public Page<DetailRule> findDetailRuleByMultiConditionAndPage(PageUtils pageUtils) {
      /* 按细则ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "ruleId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        Page<DetailRule> detailRulePage = detailRuleDao.findAll(new Specification<DetailRule>() {

            @Override
            public Predicate toPredicate(Root<DetailRule> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        return detailRulePage;
    }

    /**
     * 拼接SQL
     *
     * @param root
     * @param builder
     * @param pageUtils
     * @return
     */
    private List<Predicate> createMultiConditionSQL(Root<DetailRule> root, CriteriaBuilder builder, PageUtils pageUtils) {
        DetailRule detailRule = pageUtils.getDetailRule();
        List<Predicate> predicates = new ArrayList<Predicate>();
        /* 加入细则大类 */
        if (null != detailRule.getMainRuleId()) {
            predicates.add(builder.equal(root.get("mainRuleId"), detailRule.getMainRuleId()));
        }
         /* 加入细则状态 */
        if (null != detailRule.getRuleState()) {
            predicates.add(builder.equal(root.get("ruleState"), detailRule.getRuleState()));
        }
        /* 加入细则名称，模糊查询 */
        if (StringUtils.isNotBlank(detailRule.getRuleName())) {
            predicates.add(builder.like(root.get("ruleName"), "%" + detailRule.getRuleName() + "%"));
        }
        return predicates;
    }
}
