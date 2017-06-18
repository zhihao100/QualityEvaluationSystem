package com.lzh.qes.service.impl;

import com.lzh.qes.bean.Application;
import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.bean.ItemRule;
import com.lzh.qes.bean.MainRule;
import com.lzh.qes.dao.ApplicationDao;
import com.lzh.qes.dao.DetailRuleDao;
import com.lzh.qes.dao.ItemRuleDao;
import com.lzh.qes.dao.MainRuleDao;
import com.lzh.qes.modal.vo.DetailRuleVO;
import com.lzh.qes.modal.vo.ItemRuleVO;
import com.lzh.qes.service.IApplicationService;
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
 * Created by liuzhihao on 2017/5/15.
 */
@Service
public class ApplicationService implements IApplicationService {
    @Autowired
    private DetailRuleDao detailRuleDao;
    @Autowired
    private MainRuleDao mainRuleDao;
    @Autowired
    private ItemRuleDao itemRuleDao;
    @Autowired
    private ApplicationDao applicatonDao;

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

    /**
     * 查询细则详情
     *
     * @param ruleId
     * @return
     */
    public DetailRuleVO showDetailRuleInfo(Integer ruleId) {
        DetailRule detailRule = detailRuleDao.findByRuleId(ruleId);
        MainRule mainRule = mainRuleDao.findMainRuleByRuleId(detailRule.getMainRuleId());
        DetailRuleVO detailRuleVO = new DetailRuleVO();
        detailRuleVO.setDetailRule(detailRule);
        detailRuleVO.setMainRule(mainRule);
        return detailRuleVO;
    }

    /**
     * 根据细则ID查找该细则所有项目
     */
    @Override
    public ItemRuleVO findItemRuleByDetailRuleIdList(Integer detailRuleId) {
        List<ItemRule> itemRuleList = itemRuleDao.findByDetailRuleId(detailRuleId);
        ItemRuleVO itemRuleVO = new ItemRuleVO();
        itemRuleVO.setDetailRuleName(showDetailRuleInfo(detailRuleId).getDetailRule().getRuleName());
        itemRuleVO.setMainRule(showDetailRuleInfo(detailRuleId).getMainRule());
        itemRuleVO.setItemRuleList(itemRuleList);
        return itemRuleVO;
    }

    /**
     * 新增申请
     *
     * @param applicationList
     * @return
     */
    @Override
    public String applicationAdd(List<Application> applicationList) {
        for (Application application : applicationList) {
            Application newApplication = new Application();
            newApplication.setStudentId(application.getStudentId());
            newApplication.setStudentName(application.getStudentName());
            newApplication.setDetailRuleId(application.getDetailRuleId());
            newApplication.setMainRuleId(application.getMainRuleId());
            newApplication.setInstituteId(application.getInstituteId());
            newApplication.setItemRuleId(application.getItemRuleId());
            newApplication.setApplicationState(application.getApplicationState());
            applicatonDao.save(newApplication);
        }
        return "申请成功";
    }

    @Override
    public String applicationDelete(Long studentId, Integer itemId) {
        if (studentId == null || itemId == null) {
            return "撤回失败";
        }
        applicatonDao.deleteApplicationByStudentIdAndItemRuleId(studentId, itemId);
        return "撤回成功";
    }

    @Override
    public List<Application> findApplicationByStudentIdAndDetailRuleIdList(Long studentId, Integer detailRuleId) {
        if (studentId != null) {
            return applicatonDao.findApplicationByStudentIdAndDetailRuleId(studentId, detailRuleId);
        }
        return null;
    }
}
