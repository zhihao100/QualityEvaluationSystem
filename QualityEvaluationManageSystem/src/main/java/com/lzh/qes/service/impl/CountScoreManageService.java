package com.lzh.qes.service.impl;

import com.lzh.qes.bean.Application;
import com.lzh.qes.dao.ApplicationDao;
import com.lzh.qes.dao.ItemRuleDao;
import com.lzh.qes.enums.ApplicationState;
import com.lzh.qes.modal.vo.ApplicationVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.ICountScoreManageService;
import com.lzh.qes.service.IDetailRuleService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzhihao on 2017/5/23.
 */
@Service
public class CountScoreManageService implements ICountScoreManageService {
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private IDetailRuleService iDetailRuleService;
    @Autowired
    private ItemRuleDao itemRuleDao;
    @Autowired
    private IMainRuleManageService iMainRuleManageService;

    /**
     * 多条件分页查询申请
     *
     * @param pageUtils
     * @return
     */
    @Override
    public PageList<ApplicationVO> findApplicationByMultiConditionAndPage(PageUtils pageUtils) {
       /* 按申请ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "applicationId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        Page<Application> applicationPage = applicationDao.findAll(new Specification<Application>() {

            @Override
            public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        List<ApplicationVO> voList = new ArrayList<>();
        PageList<ApplicationVO> voPageList = new PageList<>();
        for (Application application : applicationPage.getContent()) {
            ApplicationVO applicationVO = new ApplicationVO();
            if (application != null && application.getApplicationId() != null) {
                applicationVO.setItemName(itemRuleDao.findByItemId(application.getItemRuleId()).getItemName());
                applicationVO.setDetailRuleName(iDetailRuleService.showDetailRuleInfo(application.getDetailRuleId()).getDetailRule().getRuleName());
                applicationVO.setMainRuleName(iMainRuleManageService.showMainRuleDetails(application.getMainRuleId()).getRuleName());
                applicationVO.setApplication(application);
            }
            voList.add(applicationVO);
        }
        voPageList.setDataList(voList);
        Map<String, Long> map = new HashMap<>();
        map.put("totalElements", applicationPage.getTotalElements());
        voPageList.setPagersInfo(map);
        return voPageList;
    }

    /**
     * 审核申请（修改申请状态）
     *
     * @param applicationId,applicationState
     * @return
     */
    @Override
    public String updateApplicationState(Long applicationId, String applicationState) {
        if (applicationId != null && applicationState != null) {
            Application application = applicationDao.findApplicationByApplicationId(applicationId);
            if ("审核通过".equals(applicationState)) {
                application.setApplicationState(ApplicationState.审核通过);
            }
            if ("审核未通过".equals(applicationState)) {
                application.setApplicationState(ApplicationState.审核未通过);
            }
            applicationDao.save(application);
            return "审核完成";
        }
        return "审核失败";
    }

    /**
     * 拼接SQL
     *
     * @param root
     * @param builder
     * @param pageUtils
     * @return
     */
    private List<Predicate> createMultiConditionSQL(Root<Application> root, CriteriaBuilder builder, PageUtils pageUtils) {
        Application application = pageUtils.getApplication();
        List<Predicate> predicates = new ArrayList<Predicate>();
        /* 加入所属学院 */
        if (null != application.getInstituteId()) {
            predicates.add(builder.equal(root.get("instituteId"), application.getInstituteId()));
        }
          /* 加入申请状态 */
        if (null != application.getApplicationState()) {
            predicates.add(builder.equal(root.get("applicationState"), application.getApplicationState()));
        }
            /* 加入申请所属细则 */
        if (null != application.getDetailRuleId()) {
            predicates.add(builder.equal(root.get("detailRuleId"), application.getApplicationId()));
        }
            /* 加入申请所属大类 */
        if (null != application.getMainRuleId()) {
            predicates.add(builder.equal(root.get("mainRuleId"), application.getMainRuleId()));
        }
        /* 加入申请人，模糊查询 */
        if (StringUtils.isNotBlank(application.getStudentName())) {
            predicates.add(builder.like(root.get("studentName"), "%" + application.getStudentName() + "%"));
        }
        return predicates;
    }
}
