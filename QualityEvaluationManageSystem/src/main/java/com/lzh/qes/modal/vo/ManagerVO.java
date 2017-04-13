package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.Manager;

import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/4/12.
 */
public class ManagerVO implements Serializable {
    /**
     * 管理员实体
     */
    private Manager manager;
    /**
     * 所有管理员数据条数
     */
    private Long totalManager;

    /**
     * 所属学院名称
     */
    private String instituteName;

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Long getTotalManager() {
        return totalManager;
    }

    public void setTotalManager(Long totalManager) {
        this.totalManager = totalManager;
    }
}
