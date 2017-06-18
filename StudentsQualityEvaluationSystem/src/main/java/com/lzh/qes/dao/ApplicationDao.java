package com.lzh.qes.dao;

import com.lzh.qes.bean.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liuzhihao on 2017/5/15.
 */
public interface ApplicationDao extends CrudRepository<Application, Long> {
    /**
     * 根据studentId,detailRuleId查找该学生已申请的项目
     *
     * @param studentId,detailRuleId
     * @return
     */
    List<Application> findApplicationByStudentIdAndDetailRuleId(Long studentId, Integer detailRuleId);

    /**
     * 撤回申请
     *
     * @param studentId,itemId
     * @return
     */
    @Transactional
    void deleteApplicationByStudentIdAndItemRuleId(Long studentId, Integer itemId);
}
