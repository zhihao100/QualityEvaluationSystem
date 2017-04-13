package com.lzh.qes.controller;


import com.lzh.qes.bean.ClassManage;
import com.lzh.qes.modal.vo.ClassManageVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IClassManageService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzhihao on 2017/4/12.
 */
@Controller
public class ClassManageController {
    private static Logger LOGGER = LoggerFactory.getLogger(ClassManageController.class);
    @Autowired
    private IClassManageService iClassManageService;



    /**
     * 多条件分页查询班级
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllClassByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public PageList<ClassManageVO> findAllClassByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询班级");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getClassManage()) {
            ClassManage classManage = new ClassManage();
            pageUtils.setClassManage(classManage);
        }
        PageList<ClassManageVO> classes = iClassManageService.findAllClassByMultiConditionAndPage(pageUtils);
        LOGGER.info("班级数据条数" + classes.getPagersInfo().toString());
        return classes;
    }
}
