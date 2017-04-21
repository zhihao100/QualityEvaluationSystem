package com.lzh.qes.service.impl;

import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.bean.MainRule;
import com.lzh.qes.dao.DetailRuleDao;
import com.lzh.qes.dao.MainRuleDao;
import com.lzh.qes.modal.vo.DetailRuleVO;
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
    @Autowired
    private MainRuleDao mainRuleDao;

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

    @Override
    public String createDetailRule(DetailRule detailRule) {
        if (null == detailRule.getRuleName() || "" == detailRule.getRuleName()) {
            return "请输入细则名称";
        }
        DetailRule existedDetailRule = detailRuleDao.findByMainRuleIdAndRuleName(detailRule.getMainRuleId(), detailRule.getRuleName());
        if (null != existedDetailRule) {
            return "该细则已存在";
        }
        detailRuleDao.save(detailRule);
        return "新增成功";
    }

    @Override
    public String updateDetailRuleState(DetailRule detailRule) {
        DetailRule existedDetailRule = detailRuleDao.findByRuleId(detailRule.getRuleId());
        if (null != existedDetailRule) {
            existedDetailRule.setRuleState(detailRule.getRuleState());
            detailRuleDao.save(existedDetailRule);
            return "修改完成";
        }
        return "修改失败，该细则不存在";
    }

    @Override
    public DetailRuleVO showDetailRuleInfo(Integer ruleId) {
        DetailRule detailRule = detailRuleDao.findByRuleId(ruleId);
        MainRule mainRule = mainRuleDao.findMainRuleByRuleId(detailRule.getMainRuleId());
        DetailRuleVO detailRuleVO = new DetailRuleVO();
        detailRuleVO.setDetailRule(detailRule);
        detailRuleVO.setMainRule(mainRule);
        return detailRuleVO;
    }

    @Override
    public String updateDetailRule(DetailRule detailRule) {
        if (null == detailRule.getRuleName() || "" == detailRule.getRuleName()) {
            return "请输入细则名称";
        }
        DetailRule existedDetailRule = detailRuleDao.findByRuleId(detailRule.getRuleId());
        DetailRule existedDetailRuleName = detailRuleDao.findByMainRuleIdAndRuleName(detailRule.getMainRuleId(), detailRule.getRuleName());
        if (!existedDetailRule.getRuleName().equals(detailRule.getRuleName()) && null != existedDetailRuleName) {
            return "该细则已存在";
        }
        existedDetailRule.setRuleName(detailRule.getRuleName());
        existedDetailRule.setRuleState(detailRule.getRuleState());
        existedDetailRule.setRemark(detailRule.getRemark());
        detailRuleDao.save(existedDetailRule);
        return "修改成功";
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
