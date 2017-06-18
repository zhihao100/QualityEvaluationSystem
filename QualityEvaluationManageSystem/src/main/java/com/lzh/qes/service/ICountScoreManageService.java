package com.lzh.qes.service;


import com.lzh.qes.modal.vo.ApplicationVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.utils.PageUtils;


/**
 * Created by liuzhihao on 2017/5/23.
 */
public interface ICountScoreManageService {
    /**
     * 多条件分页查询申请
     *
     * @param pageUtils
     * @return
     */
    PageList<ApplicationVO> findApplicationByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 审核申请（修改申请状态）
     *
     * @param applicationId,applicationState
     * @return
     */
    String updateApplicationState(Long applicationId, String applicationState);
}
