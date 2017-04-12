package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.Manager;

/**
 * Created by liuzhihao on 2017/4/12.
 */
public class ManagerVO {
    /**
     * 管理员实体
     */
    private Manager manager;

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
}
