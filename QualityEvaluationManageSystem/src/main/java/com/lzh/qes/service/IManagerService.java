package com.lzh.qes.service;

import com.lzh.qes.bean.Manager;
import com.lzh.qes.modal.vo.ManagerVO;
import com.lzh.qes.utils.PageUtils;

import java.util.List;

/**
 * Created by liuzhihao on 2017/4/7.
 */
public interface IManagerService {
    /**
     * 根据姓名查找管理员信息
     *
     * @return
     */
    Manager findManager();

    /**
     * 添加管理员
     *
     * @param manager
     */
    String createManager(Manager manager);



    /**
     * 修改管理员状态
     *
     * @param manager
     * @return
     */
    String updateManagerState(Manager manager);

    /**
     * 多条件分页查询管理员
     *
     * @param pageUtils
     * @return
     */
    List<ManagerVO> findAllManagerByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 管理员详情
     *
     * @param managerId
     * @return
     */
    ManagerVO showManagerDetails(long managerId);

    /**
     * 修改管理员
     *
     * @param manager
     * @return
     */
    String updateManager(Manager manager);

    /**
     * 记录登陆历史
     */
    void createLoginHistory();
}
