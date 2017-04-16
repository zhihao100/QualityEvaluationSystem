package com.lzh.qes.service.impl;

import com.lzh.qes.bean.Major;
import com.lzh.qes.dao.MajorDao;
import com.lzh.qes.service.IMajorManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/13.
 */
@Service
public class MajorManageService implements IMajorManageService {
    @Autowired
    private MajorDao majorDao;

    /**
     * 根据学院ID查找该学院所有专业
     */
    @Transactional
    @Override
    public List<Major> findMajorByInstituteIdList(Integer instituteId) {
        return majorDao.findByInstituteId(instituteId);
    }

    /**
     * 新增专业
     *
     * @param major
     * @return
     */
    @Transactional
    @Override
    public String addMajor(Major major) {
        Major existedMajor = majorDao.findByMajorName(major.getMajorName());
        if (null != existedMajor) {
            return "该专业已存在";
        }
        majorDao.save(major);
        return "新增成功";
    }

    @Override
    public Major findMajorByMajorId(Integer majorId) {
        return majorDao.findByMajorId(majorId);
    }

    /**
     * 修改专业状态
     *
     * @param major
     * @return
     */
    @Transactional
    @Override
    public String updateMajorState(Major major) {
        Major existedMajor = majorDao.findByMajorId(major.getMajorId());
        if (null != existedMajor) {
            existedMajor.setMajorState(major.getMajorState());
            majorDao.save(existedMajor);
            return "修改成功";
        }

        return "修改失败,该专业不存在";
    }

    /**
     * 修改专业
     *
     * @param major
     * @return
     */
    @Transactional
    @Override
    public String editMajor(Major major) {
        Major existedMajor = majorDao.findByMajorId(major.getMajorId());
        Major existedMajorName = majorDao.findByMajorName(major.getMajorName());
        if (null == existedMajor) {
            return "修改失败,该专业不存在";
        }
        if (!existedMajor.getMajorName().equals(major.getMajorName()) && null != existedMajorName) {
            return "该专业已存在";
        }
        existedMajor.setMajorName(major.getMajorName());
        majorDao.save(existedMajor);
        return "修改成功";
    }
}
