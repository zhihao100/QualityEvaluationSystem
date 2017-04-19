package com.lzh.qes.dao;

import com.lzh.qes.bean.Major;
import com.lzh.qes.enums.IsEnableState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/13.
 */
public interface MajorDao extends CrudRepository<Major, Integer> {
    /**
     * 根据学院ID查找该学院所有专业
     */
    List<Major> findByInstituteId(Integer instituteId);

    /**
     * 根据学院ID查找该学院所有启用专业
     */
    List<Major> findByInstituteIdAndMajorState(Integer instituteId, IsEnableState majorState);

    /**
     * 通过专业ID查询该专业信息
     */
    Major findByMajorId(Integer majorId);

    /**
     * 通过专业名称查询该专业信息
     */
    Major findByMajorName(String majorName);
}
