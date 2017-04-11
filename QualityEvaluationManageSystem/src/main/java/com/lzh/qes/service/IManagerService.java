package com.lzh.qes.service;

import org.springframework.data.domain.Page;
import com.lzh.qes.bean.Manager;
import com.lzh.qes.utils.PageUtils;

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
    Page<Manager> findAllManagerByMultiConditionAndPage(PageUtils pageUtils);

    /**
     * 管理员详情
     *
     * @param managerId
     * @return
     */
    Manager showManagerDetails(long managerId);

    /**
     * 修改管理员
     *
     * @param manager
     * @return
     */
    String updateManager(Manager manager);
}
