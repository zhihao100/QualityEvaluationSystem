package com.lzh.qes.service.impl;

import com.lzh.qes.bean.Major;
import com.lzh.qes.bean.Student;
import com.lzh.qes.dao.StudentDao;
import com.lzh.qes.modal.vo.StudentVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IClassManageService;
import com.lzh.qes.service.IInstituteManageService;
import com.lzh.qes.service.IMajorManageService;
import com.lzh.qes.service.IStudentManageService;
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
 * Created by liuzhihao on 2017/4/16.
 */
@Service
public class StudentManageService implements IStudentManageService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private IInstituteManageService iInstituteManageService;
    @Autowired
    private IClassManageService iClassManageService;
    @Autowired
    private IMajorManageService iMajorManageService;

    @Override
    public PageList<StudentVO> findAllStudentByMultiConditionAndPage(PageUtils pageUtils) {
         /* 按学生ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "studentId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        Page<Student> studentPage = studentDao.findAll(new Specification<Student>() {

            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                /* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        List<StudentVO> voList = new ArrayList<>();
        PageList<StudentVO> voPageList = new PageList<>();
        for (Student student : studentPage.getContent()) {
            StudentVO studentVO = new StudentVO();
            if (student != null && student.getInstituteId() != null && student.getClassId() != null) {
                studentVO.setInstituteName(iInstituteManageService.showInstituteDetails(student.getInstituteId()).getInstituteName());
                studentVO.setClassShortName(iClassManageService.showClassDetails(student.getClassId()).getClassManage().getClassShortName());
                studentVO.setMajorName(iMajorManageService.findMajorByMajorId(student.getMajorId()).getMajorName());
                studentVO.setStudent(student);
            }
            voList.add(studentVO);
        }
        voPageList.setDataList(voList);
        Map<String, Long> map = new HashMap<>();
        map.put("totalElements", studentPage.getTotalElements());
        voPageList.setPagersInfo(map);
        return voPageList;
    }

    /**
     * 拼接SQL
     *
     * @param root
     * @param builder
     * @param pageUtils
     * @return
     */
    private List<Predicate> createMultiConditionSQL(Root<Student> root, CriteriaBuilder builder, PageUtils pageUtils) {
        Student student = pageUtils.getStudent();
        Major major = pageUtils.getMajor();
        List<Predicate> predicates = new ArrayList<Predicate>();
        /* 加入学生学号 */
        if (null != student.getStudentNumber()) {
            predicates.add(builder.equal(root.get("studentNumber"), student.getStudentNumber()));
        }
        /* 加入学生所属学院 */
        if (null != student.getInstituteId()) {
            predicates.add(builder.equal(root.get("instituteId"), student.getInstituteId()));
        }
		/* 加入学生名，模糊查询 */
        if (StringUtils.isNotBlank(student.getStudentName())) {
            predicates.add(builder.like(root.get("studentName"), "%" + student.getStudentName() + "%"));
        }
        /* 加入学生所属专业 */
        if (null != student.getMajorId()) {
            predicates.add(builder.equal(root.get("majorId"), student.getMajorId()));
        }
        /* 加入学生所属班级，模糊查询 */
        if (null != student.getClassId()) {
            if (StringUtils.isNotBlank(iClassManageService.showClassDetails(student.getClassId()).getClassManage().getClassFullName())) {
                predicates.add(builder.like(root.get("classFullName"), "%" + iClassManageService.showClassDetails(student.getClassId()).getClassManage().getClassFullName() + "%"));
            }
        }
        return predicates;
    }

    @Override
    public StudentVO showStudentDetails(Integer studentId) {
        return null;
    }

    @Override
    public String updateStudent(Student student) {
        return null;
    }

    @Override
    public String createStudent(Student student) {
        return null;
    }
}
