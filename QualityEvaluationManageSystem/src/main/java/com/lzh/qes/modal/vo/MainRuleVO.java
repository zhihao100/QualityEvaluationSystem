package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.MainRule;

import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/4/17.
 */
public class MainRuleVO implements Serializable {
    /**
     * 细则大类实体
     */
    private MainRule mainRule;

    /**
     * 所属学院名称
     */
    private String instituteName;

    public MainRule getMainRule() {
        return mainRule;
    }

    public void setMainRule(MainRule mainRule) {
        this.mainRule = mainRule;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
}
