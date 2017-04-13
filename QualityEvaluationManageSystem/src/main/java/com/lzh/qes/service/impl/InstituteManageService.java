package com.lzh.qes.service.impl;

import com.lzh.qes.bean.Institute;
import com.lzh.qes.dao.InstituteDao;
import com.lzh.qes.service.IInstituteManageService;
import com.lzh.qes.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhihao on 2017/4/11.
 */
@Service
public class InstituteManageService implements IInstituteManageService {
    @Autowired
    private InstituteDao instituteDao;

    @Transactional
    @Override
    public String createInstitute(Institute institute) {
        Institute existedInstitute = instituteDao.findByInstituteName(institute.getInstituteName());
        if (null == existedInstitute) {
            instituteDao.save(institute);
            return "添加成功";
        }
        return "该学院已存在";
    }


    @Override
    public String updateInstituteState(Institute institute) {
        Institute existedInstitute = instituteDao.findByInstituteId(institute.getInstituteId());
        if (null != existedInstitute) {
            existedInstitute.setInstituteState(institute.getInstituteState());
            instituteDao.save(existedInstitute);
            return "修改成功";
        }
        return "该学院不存在";
    }

    @Override
    public Page<Institute> findAllInstituteByMultiConditionAndPage(PageUtils pageUtils) {

       /* 按学院ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "instituteId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        return instituteDao.findAll(new Specification<Institute>() {

            @Override
            public Predicate toPredicate(Root<Institute> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
    }

    /**
     * 拼接SQL
     *
     * @param root
     * @param builder
     * @param pageUtils
     * @return
     */
    private List<Predicate> createMultiConditionSQL(Root<Institute> root, CriteriaBuilder builder, PageUtils pageUtils) {
        Institute institute = pageUtils.getInstitute();
        List<Predicate> predicates = new ArrayList<Predicate>();
        /* 加入学院状态 */
        if (null != institute.getInstituteState()) {
            predicates.add(builder.equal(root.get("instituteState"), institute.getInstituteState()));
        }
        /* 加入学院名，模糊查询 */
        if (StringUtils.isNotBlank(institute.getInstituteName())) {
            predicates.add(builder.like(root.get("instituteName"), "%" + institute.getInstituteName() + "%"));
        }
        return predicates;
    }

    /**
     * 查找所有学院
     *
     * @return
     */
    @Override
    public List<Institute> findAllInstitute() {
        return instituteDao.findAll();
    }

    @Override
    public Institute showInstituteDetails(int instituteId) {
        return instituteDao.findByInstituteId(instituteId);
    }

    @Override
    public String updateInstitute(Institute institute) {
        Institute existedInstitute = instituteDao.findByInstituteId(institute.getInstituteId());
        Institute existedInstituteName = instituteDao.findByInstituteName(institute.getInstituteName());
        if (null == existedInstitute) {
            return "该学院不存在";
        }
        if (!existedInstitute.getInstituteName().equals(institute.getInstituteName()) && null != existedInstituteName) {
            return "该学院已存在";
        }
        existedInstitute.setInstituteName(institute.getInstituteName());
        existedInstitute.setInstituteState(institute.getInstituteState());
        instituteDao.save(institute);
        return "修改成功";
    }
}
