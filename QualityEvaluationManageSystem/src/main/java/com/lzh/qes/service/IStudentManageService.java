package com.lzh.qes.service;

import com.lzh.qes.bean.Student;
import com.lzh.qes.modal.vo.StudentVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.utils.PageUtils;

/**
 * Created by liuzhihao on 2017/4/16.
 */
public interface IStudentManageService {
    /**
     * @param pageUtils
     * @return
     */
    PageList<StudentVO> findAllStudentByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 学生详情
     *
     * @param studentId
     * @return
     */
    StudentVO showStudentDetails(Integer studentId);

    /**
     * 修改学生
     *
     * @param student
     * @return
     */
    String updateStudent(Student student);

    /**
     * 新增学生
     *
     * @param student
     * @return
     */
    String createStudent(Student student);
}
