package com.lzh.qes.dao;

import com.lzh.qes.bean.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by liuzhihao on 2017/4/7.
 */
public interface ManagerDao extends CrudRepository<Manager, Long> {
    /**
     * 根据姓名查找管理员信息
     *
     * @param name
     * @return
     */
    Manager findByManagerName(String name);

    /**
     * 根据ID查找管理员信息
     *
     * @param managerId
     * @return
     */
    Manager findByManagerId(long managerId);

    /**
     * 多条件分页查询管理员
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<Manager> findAll(Specification<Manager> spec, Pageable pageable);
}
