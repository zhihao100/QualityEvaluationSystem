package com.lzh.qes.dao;


import com.lzh.qes.bean.Student;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by liuzhihao on 2017/4/25.
 */
public interface UserDao extends CrudRepository<Student, Long> {
    /**
     * 根据学生学号查找学生信息
     *
     * @param stuNumber
     * @return
     */
    Student findByStudentNumber(String stuNumber);
}
