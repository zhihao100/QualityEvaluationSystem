package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/4/18.
 */
@Entity
public class DetailRule implements Serializable {
    /**
     * 细则编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ruleId;
    /**
     * 细则名称
     */
    @NotBlank
    private String ruleName;
    /**
     * 所属大类
     */
    @NotNull
    private Integer mainRuleId;
    /**
     * 细则状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState ruleState;
    /**
     * 细则备注
     */
    @NotBlank
    private String remark;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getMainRuleId() {
        return mainRuleId;
    }

    public void setMainRuleId(Integer mainRuleId) {
        this.mainRuleId = mainRuleId;
    }

    public IsEnableState getRuleState() {
        return ruleState;
    }

    public void setRuleState(IsEnableState ruleState) {
        this.ruleState = ruleState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
