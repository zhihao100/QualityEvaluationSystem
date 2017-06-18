package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.ItemRule;
import com.lzh.qes.bean.MainRule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuzhihao on 2017/4/21.
 */
public class ItemRuleVO implements Serializable {
    /**
     * 项目实体
     */
    private List<ItemRule> itemRuleList;
    /**
     * 所属细则名称
     */
    private String detailRuleName;
    /**
     * 所属大类
     */
    private MainRule mainRule;

    public List<ItemRule> getItemRuleList() {
        return itemRuleList;
    }

    public void setItemRuleList(List<ItemRule> itemRuleList) {
        this.itemRuleList = itemRuleList;
    }

    public String getDetailRuleName() {
        return detailRuleName;
    }

    public void setDetailRuleName(String detailRuleName) {
        this.detailRuleName = detailRuleName;
    }

    public MainRule getMainRule() {
        return mainRule;
    }

    public void setMainRule(MainRule mainRule) {
        this.mainRule = mainRule;
    }
}
