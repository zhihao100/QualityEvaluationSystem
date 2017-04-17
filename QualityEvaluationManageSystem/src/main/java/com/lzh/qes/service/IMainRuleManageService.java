package com.lzh.qes.service;

import com.lzh.qes.modal.vo.MainRuleVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.utils.PageUtils;

/**
 * Created by liuzhihao on 2017/4/17.
 */
public interface IMainRuleManageService {
    /**
     * 多条件分页查询细则大类
     *
     * @param pageUtils
     * @return
     */
    PageList<MainRuleVO> findAllMainRuleByMultiConditionAndPage(PageUtils pageUtils);
}
