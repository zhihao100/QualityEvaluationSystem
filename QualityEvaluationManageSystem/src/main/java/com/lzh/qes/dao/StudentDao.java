package com.lzh.qes.dao;

import com.lzh.qes.bean.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by liuzhihao on 2017/4/16.
 */
public interface StudentDao extends CrudRepository<Student, Integer> {
    /**
     * 多条件分页查询学生
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<Student> findAll(Specification<Student> spec, Pageable pageable);

    /**
     * 通过学生ID查询该学生详情
     * @param student
     * @return
     */
    Student findStudentByStudentId(Integer student);
    /**
     * 通过学生学号studentNumber查询该学生详情
     * @param studentNumber
     * @return
     */
    Student findStudentByStudentNumber(Integer studentNumber);
}
