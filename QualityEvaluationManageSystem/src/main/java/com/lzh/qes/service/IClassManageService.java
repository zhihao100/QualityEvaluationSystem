package com.lzh.qes.service;

import com.lzh.qes.bean.ClassManage;
import com.lzh.qes.modal.vo.ClassManageVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.utils.PageUtils;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/14.
 */
public interface IClassManageService {
    /**
     *多条件分页查询班级
     * @param pageUtils
     * @return
     */
    PageList<ClassManageVO> findAllClassByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 班级详情
     *
     * @param classId
     * @return
     */
    ClassManageVO showClassDetails(Integer classId);
    /**
     * 修改班级状态
     *
     * @param classManage
     * @return
     */
    String updateClassState(ClassManage classManage);
    /**
     * 修改班级
     *
     * @param classManage
     * @return
     */
    String updateClass(ClassManage classManage);
    /**
     * 新增班级
     *
     * @param classManage
     * @return
     */
    String createClass(ClassManage classManage);

    /**
     * 根据专业majorId查询班级
     * @param majorId
     * @return
     */
    List<ClassManage> findAllClassByMajorId(Integer majorId);

}
