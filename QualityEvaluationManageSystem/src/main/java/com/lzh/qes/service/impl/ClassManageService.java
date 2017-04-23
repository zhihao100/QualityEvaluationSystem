package com.lzh.qes.service.impl;

import com.lzh.qes.bean.ClassManage;
import com.lzh.qes.dao.ClassManageDao;
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
 * Created by 新乐 on 2017/4/13.
 */
@Service
public class ClassManageService implements IClassManageService {
    @Autowired
    private ClassManageDao classManageDao;
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

        Page<ClassManage> classManagePage = classManageDao.findAll(new Specification<ClassManage>() {

            @Override
            public Predicate toPredicate(Root<ClassManage> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder,pageUtils);
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
        ClassManage classManage = classManageDao.findByClassId(classId);
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
        /* 加入所属学院 */
        if (null != classManage.getInstituteId()) {
            predicates.add(builder.equal(root.get("instituteId"), classManage.getInstituteId()));
        }
        /* 加入所属专业 */
        if (null != classManage.getMajorId()) {
            predicates.add(builder.equal(root.get("majorId"), classManage.getMajorId()));
        }
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
    @Override
    public String updateClassState(ClassManage classManage) {
        if (null != (Integer) classManage.getClassId()) {
            ClassManage existedClass = classManageDao.findByClassId(classManage.getClassId());
            existedClass.setClassState(classManage.getClassState());
            classManageDao.save(existedClass);
            return "修改完成";
        }
        return "该班级不存在";
    }

    @Override
    public String updateClass(ClassManage classManage) {
        if(null==classManage.getInstituteId()){
            return "请选择学院再修改";
        }
        if(null==classManage.getMajorId()){
            return "请选择专业再修改";
        }
        if(null==classManage.getGrade()){
            return "请选择年级再修改";
        }
        if(null==classManage.getClassNumber()){
            return "请录入班级序号再修改";
        }
        if (null == (Integer) classManage.getClassId()) {
            return "修改失败，该班级不存在";
        }
        ClassManage existedClass = classManageDao.findByClassId(classManage.getClassId());
        ClassManage existedClassFullName = classManageDao.findByClassFullName(classManage.getClassFullName());
        if (!existedClass.getClassFullName().equals(classManage.getClassFullName()) && null != existedClassFullName) {
            return "该班级已存在";
        }
        existedClass.setInstituteId(classManage.getInstituteId());
        existedClass.setMajorId(classManage.getMajorId());
        existedClass.setGrade(classManage.getGrade());
        existedClass.setClassFullName(classManage.getClassFullName());
        existedClass.setClassShortName(classManage.getClassShortName());
        existedClass.setClassNumber(classManage.getClassNumber());
        existedClass.setClassState(classManage.getClassState());
        classManageDao.save(existedClass);
        return "修改成功";
    }

    @Override
    public String createClass(ClassManage classManage) {
        if(null==classManage.getInstituteId()){
            return "请选择学院再添加";
        }
        if(null==classManage.getMajorId()){
            return "请选择专业再添加";
        }
        if(null==classManage.getGrade()){
            return "请选择年级再添加";
        }
        if(null==classManage.getClassNumber()){
            return "请录入班级序号再添加";
        }
        ClassManage existedClass = classManageDao.findByClassFullName(classManage.getClassFullName());
        if(null==existedClass){
            classManageDao.save(classManage);
            return "新增成功";
        }
        return "该班级已存在";
    }

    @Override
    public List<ClassManage> findAllClassByMajorId(Integer majorId) {
        return classManageDao.findAllClassByMajorId(majorId);
    }
}
