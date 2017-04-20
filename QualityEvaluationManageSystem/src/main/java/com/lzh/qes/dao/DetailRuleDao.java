package com.lzh.qes.dao;


import com.lzh.qes.bean.DetailRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by liuzhihao on 2017/4/20.
 */
public interface DetailRuleDao extends CrudRepository<DetailRule, Long> {
    /**
     * 多条件分页查询某学院某大类的细则
     *
     * @param spec
     * @param pageable
     * @return
     */
    Page<DetailRule> findAll(Specification<DetailRule> spec, Pageable pageable);
}
