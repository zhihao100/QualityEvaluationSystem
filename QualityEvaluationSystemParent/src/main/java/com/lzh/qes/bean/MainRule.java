package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by liuzhihao on 2017/4/17.
 */
@Entity
public class MainRule {
    /**
     * 细则大类编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ruleId;
    /**
     * 细则大类名称
     */
    @NotBlank
    private String ruleName;
    /**
     * 所占权重
     */
    @NotNull
    private Double weight;

    /**
     * 规则状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState ruleState;

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public IsEnableState getRuleState() {
        return ruleState;
    }

    public void setRuleState(IsEnableState ruleState) {
        this.ruleState = ruleState;
    }
}
