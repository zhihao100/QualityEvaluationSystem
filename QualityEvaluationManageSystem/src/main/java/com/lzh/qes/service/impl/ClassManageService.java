package com.lzh.qes.service.impl;

import com.lzh.qes.bean.ClassManage;
import com.lzh.qes.dao.ClassManageDAO;
import com.lzh.qes.dao.InstituteDao;
import com.lzh.qes.dao.MajorDao;
import com.lzh.qes.modal.vo.ClassManageVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IClassManageService;
import com.lzh.qes.service.IInstituteManageService;
import com.lzh.qes.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 新乐 on 2017/4/13.
 */
public class ClassManageService implements IClassManageService {
    @Autowired
    private ClassManageDAO classManageDAO;
    @Autowired
    private IInstituteManageService iInstituteManageService;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private InstituteDao instituteDao;

    @Override
    public PageList<ClassManageVO> findAllClassByMultiConditionAndPage(PageUtils pageUtils) {
          /* 按班级ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "classId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        Page<ClassManage> classManagePage = classManageDAO.findAll(new Specification<ClassManage>() {

            @Override
            public Predicate toPredicate(Root<ClassManage> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        List<ClassManageVO> voList = new ArrayList<>();
        PageList<ClassManageVO> voPageList = new PageList<>();
        for (ClassManage classManage : classManagePage.getContent()) {
            ClassManageVO classManageVO = new ClassManageVO();
            if (classManage != null && classManage.getInstituteId() != null) {
                classManageVO.setInstituteName(iInstituteManageService.showInstituteDetails(classManage.getInstituteId()).getInstituteName());
                classManageVO.setMajorName(majorDao.findByMajorId(classManage.getMajorId()).getMajorName());
                classManageVO.setClassManage(classManage);
            }
            voList.add(classManageVO);
        }
        voPageList.setDataList(voList);
        Map<String, Long> map = new HashMap<>();
        map.put("totalElements", classManagePage.getTotalElements());
        voPageList.setPagersInfo(map);
        return voPageList;
    }

    @Override
    public ClassManageVO showClassDetails(Integer classId) {
        ClassManage classManage = classManageDAO.findByClassId(classId);
        ClassManageVO classManageVO = new ClassManageVO();
        classManageVO.setClassManage(classManage);
        classManageVO.setInstituteName(instituteDao.findByInstituteId(classManage.getInstituteId()).getInstituteName());
        classManageVO.setMajorName(majorDao.findByMajorId(classManage.getMajorId()).getMajorName());
        return classManageVO;
    }

    /**
     * 拼接SQL
     *
     * @param root
     * @param builder
     * @param pageUtils
     * @return
     */
    private List<Predicate> createMultiConditionSQL(Root<ClassManage> root, CriteriaBuilder builder, PageUtils pageUtils) {
        ClassManage classManage = pageUtils.getClassManage();
        List<Predicate> predicates = new ArrayList<Predicate>();
		/* 加入班级状态 */
        if (null != classManage.getClassState()) {
            predicates.add(builder.equal(root.get("classState"), classManage.getClassState()));
        }
		/* 加入班级名，模糊查询 */
        if (StringUtils.isNotBlank(classManage.getClassFullName())) {
            predicates.add(builder.like(root.get("classFullName"), "%" + classManage.getClassFullName() + "%"));
        }
        return predicates;
    }
}
