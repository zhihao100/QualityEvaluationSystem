package com.lzh.qes.dao;

import com.lzh.qes.bean.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by liuzhihao on 2017/5/23.
 */
public interface ApplicationDao extends CrudRepository<Application, Long> {
    /**
     * 多条件分页查询申请
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<Application> findAll(Specification<Application> spec, Pageable pageable);

    /**
     * 通过applicationId查询该申请详情
     *
     * @param applicationId
     * @return
     */
    Application findApplicationByApplicationId(Long applicationId);
}
