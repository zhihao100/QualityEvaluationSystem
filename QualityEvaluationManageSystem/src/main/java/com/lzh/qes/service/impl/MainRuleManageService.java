package com.lzh.qes.service.impl;

import com.lzh.qes.bean.MainRule;
import com.lzh.qes.dao.MainRuleDao;
import com.lzh.qes.modal.vo.MainRuleVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IInstituteManageService;
import com.lzh.qes.service.IMainRuleManageService;
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
import java.util.*;


/**
 * Created by liuzhihao on 2017/4/17.
 */
@Service
public class MainRuleManageService implements IMainRuleManageService {
    @Autowired
    private MainRuleDao mainRuleDao;
    @Autowired
    private IInstituteManageService iInstituteManageService;

    @Override
    public PageList<MainRuleVO> findAllMainRuleByMultiConditionAndPage(PageUtils pageUtils) {
          /* 按细则大类ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "ruleId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        Page<MainRule> mainRulePage = mainRuleDao.findAll(new Specification<MainRule>() {

            @Override
            public Predicate toPredicate(Root<MainRule> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        List<MainRuleVO> voList = new ArrayList<>();
        PageList<MainRuleVO> voPageList = new PageList<>();
        for (MainRule mainRule : mainRulePage.getContent()) {
            MainRuleVO mainRuleVO = new MainRuleVO();
            if (mainRule != null && mainRule.getInstituteId() != null) {
                mainRuleVO.setInstituteName(iInstituteManageService.showInstituteDetails(mainRule.getInstituteId()).getInstituteName());
                mainRuleVO.setMainRule(mainRule);
            }
            voList.add(mainRuleVO);
        }
        voPageList.setDataList(voList);
        Map<String, Long> map = new HashMap<>();
        map.put("totalElements", mainRulePage.getTotalElements());
        voPageList.setPagersInfo(map);
        return voPageList;
    }

    @Override
    public String updateMainRuleState(MainRule mainRule) {
        MainRule existedMainRule = mainRuleDao.findMainRuleByRuleId(mainRule.getRuleId());
        if (existedMainRule != null) {
            existedMainRule.setRuleState(mainRule.getRuleState());
            mainRuleDao.save(existedMainRule);
            return "修改成功";
        }
        return "修改失败，类别不存在";
    }

    @Override
    public MainRuleVO showMainRuleDetails(Integer ruleId) {
        MainRule mainRule = mainRuleDao.findMainRuleByRuleId(ruleId);
        MainRuleVO mainRuleVO = new MainRuleVO();
        mainRuleVO.setInstituteName(iInstituteManageService.showInstituteDetails(mainRule.getInstituteId()).getInstituteName());
        mainRuleVO.setMainRule(mainRule);
        return mainRuleVO;
    }

    @Override
    public String createMainRule(MainRule mainRule) {
        if (null == mainRule.getRuleName()) {
            return "请录入类别名称";
        }
        if (null == mainRule.getInstituteId()) {
            return "请选择学院";
        }
        if (null == mainRule.getWeight()) {
            return "请录入权重";
        }
        MainRule existedMainRule = mainRuleDao.findMainRuleByRuleNameAndInstituteId(mainRule.getRuleName(), mainRule.getInstituteId());
        if (null == existedMainRule) {
            mainRuleDao.save(mainRule);
            return "新增成功";
        }
        return "该类别已存在";
    }

    @Override
    public String updateMainRule(MainRule mainRule) {
        if (null == mainRule.getRuleName()) {
            return "请录入类别名称再修改";
        }
        if (null == mainRule.getInstituteId()) {
            return "请选择学院再修改";
        }
        if (null == mainRule.getWeight()) {
            return "请录入权重再修改";
        }
        MainRule existedMainRule = mainRuleDao.findMainRuleByRuleId(mainRule.getRuleId());
        MainRule existedMainRuleName = mainRuleDao.findMainRuleByRuleNameAndInstituteId(mainRule.getRuleName(), mainRule.getInstituteId());
        if (!existedMainRule.getRuleName().equals(mainRule.getRuleName()) && null != existedMainRuleName) {
            return "该类别已存在";
        }
        existedMainRule.setRuleName(mainRule.getRuleName());
        existedMainRule.setInstituteId(mainRule.getInstituteId());
        existedMainRule.setWeight(mainRule.getWeight());
        existedMainRule.setRuleState(mainRule.getRuleState());
        mainRuleDao.save(existedMainRule);
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
    private List<Predicate> createMultiConditionSQL(Root<MainRule> root, CriteriaBuilder builder, PageUtils pageUtils) {
        MainRule mainRule = pageUtils.getMainRule();
        List<Predicate> predicates = new ArrayList<Predicate>();
        /* 加入细则大类所属学院 */
        if (null != mainRule.getInstituteId()) {
            predicates.add(builder.equal(root.get("instituteId"), mainRule.getInstituteId()));
        }
		/* 加入细则大类名称，模糊查询 */
        if (StringUtils.isNotBlank(mainRule.getRuleName())) {
            predicates.add(builder.like(root.get("ruleName"), "%" + mainRule.getRuleName() + "%"));
        }
        return predicates;
    }
}
