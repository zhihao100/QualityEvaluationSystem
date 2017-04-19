package com.lzh.qes.dao;

import com.lzh.qes.bean.Institute;
import com.lzh.qes.enums.IsEnableState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/11.
 */
public interface InstituteDao extends CrudRepository<Institute, Integer> {
    /**
     * 根据学院名查找学院信息
     *
     * @param name
     * @return
     */
    Institute findByInstituteName(String name);

    /**
     * 根据ID查找学院信息
     *
     * @param instituteId
     * @return
     */
    Institute findByInstituteId(int instituteId);

    /**
     * 多条件分页查询学院
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<Institute> findAll(Specification<Institute> spec, Pageable pageable);

    /**
     * 查询所有启用学院
     *
     * @return
     */
    List<Institute> findByInstituteState(IsEnableState instituteState);
}
