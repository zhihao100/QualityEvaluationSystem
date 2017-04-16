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
}
