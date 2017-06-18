package com.lzh.qes.service;

import com.lzh.qes.bean.MainRule;
import com.lzh.qes.bean.Student;
import com.lzh.qes.utils.PageUtils;
import org.springframework.data.domain.Page;

/**
 * Created by liuzhihao on 2017/5/15.
 */
public interface IIndexService {
    /**
     * 根据学号查找用户信息
     *
     * @return
     */
    Student findUser();

    /**
     * 多条件分页查询细则大类
     *
     * @param pageUtils
     * @return
     */
    Page<MainRule> findAllMainRuleByMultiConditionAndPage(PageUtils pageUtils);
}
