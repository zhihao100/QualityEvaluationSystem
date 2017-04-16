package com.lzh.qes.service;

import com.lzh.qes.bean.Major;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/13.
 */
public interface IMajorManageService {
    /**
     * 根据学院ID查找该学院所有专业
     */
    List<Major> findMajorByInstituteIdList(Integer instituteId);

    /**
     * 修改专业状态
     *
     * @param major
     */
    String updateMajorState(Major major);

    /**
     * 修改专业
     *
     * @param major
     */
    String editMajor(Major major);

    /**
     * 新增专业
     *
     * @param major
     */
    String addMajor(Major major);

    /**
     * 通过ID查找该专业
     *
     * @param majorId
     */
    Major findMajorByMajorId(Integer majorId);
}
