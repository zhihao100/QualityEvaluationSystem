package com.lzh.qes.dao;

import com.lzh.qes.bean.ClassManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 新乐 on 2017/4/13.
 */
public interface ClassManageDAO extends CrudRepository<ClassManage, Integer> {
    /**
     * 多条件分页查询班级
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<ClassManage> findAll(Specification<ClassManage> spec, Pageable pageable);

    /**
     * 根据班级ID查询班级信息
     *
     * @param classId
     * @return
     */
    ClassManage findByClassId(Integer classId);
    /**
     * 根据班级全称查询班级信息
     *
     * @param classFullName
     * @return
     */
    ClassManage findByClassFullName(String classFullName);

}
