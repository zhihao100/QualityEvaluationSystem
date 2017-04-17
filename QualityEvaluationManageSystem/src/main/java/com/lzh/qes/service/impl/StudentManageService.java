package com.lzh.qes.service.impl;

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
         /* 加入学生所属年级 */
        if (null != student.getGrade()) {
            predicates.add(builder.equal(root.get("grade"), student.getGrade()));
        }
        /* 加入学生所属班级 */
        if (null != student.getClassId()) {
            predicates.add(builder.equal(root.get("classId"), student.getClassId()));
        }
        return predicates;
    }

    @Override
    public StudentVO showStudentDetails(Integer studentId) {
        Student student=studentDao.findStudentByStudentId(studentId);
        StudentVO studentVO=new StudentVO();
        studentVO.setStudent(student);
        studentVO.setInstituteName(iInstituteManageService.showInstituteDetails(student.getInstituteId()).getInstituteName());
        studentVO.setMajorName(iMajorManageService.findMajorByMajorId(student.getMajorId()).getMajorName());
        studentVO.setClassShortName(iClassManageService.showClassDetails(student.getClassId()).getClassManage().getClassShortName());
        return studentVO;
    }

    @Override
    public String updateStudent(Student student) {
        if(null==student.getStudentNumber()){
            return "请录入学号再修改";
        }
        if(null==student.getStudentName()||""==student.getStudentName()){
            return "请录入姓名再修改";
        }
        if(null==student.getInstituteId()){
            return "请选择学院再修改";
        }
        if(null==student.getMajorId()){
            return "请选择专业再修改";
        }
        if(null==student.getGrade()){
            return "请选择年级再修改";
        }
        if(null==student.getClassId()){
            return "请选择班级再修改";
        }
        Student existedStudent = studentDao.findStudentByStudentId(student.getStudentId());
        Student existedStudentNumber = studentDao.findStudentByStudentNumber(student.getStudentNumber());
        if (null == existedStudent) {
            return "修改失败，该学生不存在";
        }
        if (!existedStudent.getStudentNumber().equals(student.getStudentNumber()) && null != existedStudentNumber) {
            return "该学生已存在";
        }
        existedStudent.setStudentNumber(student.getStudentNumber());
        existedStudent.setStudentName(student.getStudentName());
        existedStudent.setGender(student.getGender());
        existedStudent.setInstituteId(student.getInstituteId());
        existedStudent.setMajorId(student.getMajorId());
        existedStudent.setGrade(student.getGrade());
        existedStudent.setClassId(student.getClassId());
        studentDao.save(existedStudent);
        return "修改成功";
    }

    @Override
    public String createStudent(Student student) {
        if(null==student.getStudentNumber()){
            return "请输入学号";
        }
        if(null==student.getStudentName()){
            return "请输入姓名";
        }
        if(null==student.getGender()){
            return "请输入性别";
        }
        if(null==student.getInstituteId()){
            return "请选择学院";
        }
        if(null==student.getMajorId()){
            return "请选择专业";
        }
        if(null==student.getGrade()){
            return "请选择年级";
        }
        if(null==student.getClassId()){
            return "请选择班级";
        }
        Student exitedStudent=studentDao.findStudentByStudentNumber(student.getStudentNumber());
        if(null==exitedStudent){
            studentDao.save(student);
            return "新增成功";
        }
        return "该学生已存在";
    }
}
