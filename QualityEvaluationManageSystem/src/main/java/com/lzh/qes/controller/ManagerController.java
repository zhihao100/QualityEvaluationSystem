package com.lzh.qes.controller;


import com.lzh.qes.bean.Manager;
import com.lzh.qes.service.IManagerService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员控制层
 */
@Controller
public class ManagerController {
    private static Logger LOGGER = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private IManagerService managerService;

    /**
     * 微酒后台登陆
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        LOGGER.info("素质测评后台登录");
        return "login";
    }

    /**
     * 访问主页面
     *
     * @return
     */
    @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
    public String index() {
        LOGGER.info("访问主页面");
        return "index";
    }

    /**
     * 获取当前登录管理员
     *
     * @return
     */
    @RequestMapping(value = "getCurrentManager", method = RequestMethod.POST)
    @ResponseBody
    public Manager getCurrentManager() {
        LOGGER.info("获取当前登录管理员");
        Manager currentManager = managerService.findManager();
        currentManager.setPassword("");
        return currentManager;
    }

    /**
     * 添加管理员
     *
     * @param manager
     * @return
     */
    @RequestMapping(value = "createManager", method = RequestMethod.POST)
    @ResponseBody
    public String createManager(@RequestBody Manager manager) {
        LOGGER.info("添加管理员");
        Assert.notNull(manager);
        return managerService.createManager(manager);
    }

    /**
     * 修改管理员
     *
     * @param manager
     * @return
     */
    @RequestMapping(value = "updateManager", method = RequestMethod.POST)
    @ResponseBody
    public String updateManager(@RequestBody Manager manager) {
        LOGGER.info("修改管理员");
        Assert.notNull(manager);
        return managerService.updateManager(manager);
    }

    /**
     * 删除管理员
     *
     * @param managerId
     * @return
     */
    @RequestMapping(value = "deleteManager", method = RequestMethod.POST)
    @ResponseBody
    public String deleteManager(@RequestBody long managerId) {
        LOGGER.info("删除管理员");
        Assert.notNull(managerId);
        return managerService.deleteManager(managerId);
    }

    /**
     * 修改管理员状态
     *
     * @param manager
     * @return
     */
    @RequestMapping(value = "updateManagerState", method = RequestMethod.POST)
    @ResponseBody
    public String updateManagerState(@RequestBody Manager manager) {
        LOGGER.info("修改管理员状态");
        Assert.notNull(manager, "管理员为空");
        return managerService.updateManagerState(manager);
    }

    /**
     * 多条件分页查询管理员
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllManagerByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public Page<Manager> findAllManagerByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询管理员");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getManager()) {
            Manager manager = new Manager();
            pageUtils.setManager(manager);
        }
        Page<Manager> managers = managerService.findAllManagerByMultiConditionAndPage(pageUtils);
        LOGGER.info("管理员数据条数" + managers.getContent().size());
        return managers;
    }

    /**
     * 管理员详情
     *
     * @param managerId
     * @return
     */
    @RequestMapping(value = "showManagerDetails", method = RequestMethod.POST)
    @ResponseBody
    public Manager showManagerDetails(@RequestBody long managerId) {
        LOGGER.info("管理员详情");
        Assert.notNull(managerId);
        return managerService.showManagerDetails(managerId);
    }

}
