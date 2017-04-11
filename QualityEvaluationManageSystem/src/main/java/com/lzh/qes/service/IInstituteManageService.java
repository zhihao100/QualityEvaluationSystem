package com.lzh.qes.service;

import com.lzh.qes.bean.Institute;
import com.lzh.qes.utils.PageUtils;
import org.springframework.data.domain.Page;

/**
 * Created by liuzhihao on 2017/4/11.
 */
public interface IInstituteManageService {

    /**
     * 添加学院
     *
     * @param institute
     */
    String createInstitute(Institute institute);



    /**
     * 修改学院状态
     *
     * @param institute
     * @return
     */
    String updateInstituteState(Institute institute);

    /**
     * 多条件分页查询学院
     *
     * @param pageUtils
     * @return
     */
    Page<Institute> findAllInstituteByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 学院详情
     *
     * @param instituteId
     * @return
     */
    Institute showInstituteDetails(int instituteId);

    /**
     * 修改学院
     *
     * @param institute
     * @return
     */
    String updateInstitute(Institute institute);
}
