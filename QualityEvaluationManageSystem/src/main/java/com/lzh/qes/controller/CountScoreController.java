package com.lzh.qes.controller;

import com.lzh.qes.bean.Application;
import com.lzh.qes.modal.vo.ApplicationVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.ICountScoreManageService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuzhihao on 2017/5/23.
 */
@Controller
public class CountScoreController {
    private static Logger LOGGER = LoggerFactory.getLogger(CountScoreController.class);
    @Autowired
    private ICountScoreManageService iCountScoreManageService;

    /**
     * 多条件分页查询申请
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findWaitApplicationByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public PageList<ApplicationVO> findWaitApplicationByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询待审核申请");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getApplication()) {
            Application application = new Application();
            pageUtils.setApplication(application);
        }
        PageList<ApplicationVO> applications = iCountScoreManageService.findApplicationByMultiConditionAndPage(pageUtils);
        LOGGER.info("申请数据条数" + applications.getPagersInfo().size());
        return applications;
    }

    /**
     * 审核申请（修改申请状态）
     *
     * @param applicationId,applicationState
     * @return
     */
    @RequestMapping(value = "updateApplicationState/{applicationId}", method = RequestMethod.POST)
    @ResponseBody
    public String updateApplicationState(@PathVariable Long applicationId, @RequestBody String applicationState) {
        LOGGER.info("审核申请（修改申请状态）");
        Assert.notNull(applicationId, "申请不存在");
        Assert.notNull(applicationState, "申请状态为空");
        return iCountScoreManageService.updateApplicationState(applicationId, applicationState);
    }
}
