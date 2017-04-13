package com.lzh.qes.service;

import com.lzh.qes.modal.vo.ClassManageVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.utils.PageUtils;


/**
 * Created by liuzhihao on 2017/4/12.
 */
public interface IClassManageService {
    PageList<ClassManageVO> findAllClassByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 班级详情
     *
     * @param classId
     * @return
     */
    ClassManageVO showClassDetails(Integer classId);
}
