package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.DetailRule;
import com.lzh.qes.bean.MainRule;

import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/4/20.
 */
public class DetailRuleVO implements Serializable {
    /**
     * 细则实体
     */
    private DetailRule detailRule;
    /**
     * 大类实体
     */
    private MainRule mainRule;

    public DetailRule getDetailRule() {
        return detailRule;
    }

    public void setDetailRule(DetailRule detailRule) {
        this.detailRule = detailRule;
    }

    public MainRule getMainRule() {
        return mainRule;
    }

    public void setMainRule(MainRule mainRule) {
        this.mainRule = mainRule;
    }
}
