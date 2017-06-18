package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.Application;

import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/5/23.
 */
public class ApplicationVO implements Serializable {
    /**
     * 申请实体
     */
    private Application application;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 项目所属细则名称
     */
    private String detailRuleName;
    /**
     * 项目所属大类名称
     */
    private String mainRuleName;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDetailRuleName() {
        return detailRuleName;
    }

    public void setDetailRuleName(String detailRuleName) {
        this.detailRuleName = detailRuleName;
    }

    public String getMainRuleName() {
        return mainRuleName;
    }

    public void setMainRuleName(String mainRuleName) {
        this.mainRuleName = mainRuleName;
    }
}
