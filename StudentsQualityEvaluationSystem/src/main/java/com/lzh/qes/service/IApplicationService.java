package com.lzh.qes.service;

import com.lzh.qes.bean.Application;
import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.modal.vo.ItemRuleVO;
import com.lzh.qes.utils.PageUtils;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by liuzhihao on 2017/5/15.
 */
public interface IApplicationService {
    /**
     * 多条件分页查询某学院某大类的细则
     *
     * @param pageUtils
     * @return
     */
    Page<DetailRule> findDetailRuleByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 根据细则ID查找该细则所有项目
     */
    ItemRuleVO findItemRuleByDetailRuleIdList(Integer detailRuleId);

    /**
     * 新增申请
     *
     * @param applicationList
     * @return
     */
    String applicationAdd(List<Application> applicationList);

    /**
     * 撤回申请
     *
     * @param studentId,itemId
     * @return
     */
    String applicationDelete(Long studentId, Integer itemId);

    /**
     * 根据studentId,detailRuleId查找该学生已申请的项目
     *
     * @param studentId,detailRuleId
     * @return
     */
    List<Application> findApplicationByStudentIdAndDetailRuleIdList(Long studentId, Integer detailRuleId);
}
