package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/4/21.
 */
@Entity
public class ItemRule implements Serializable {
    /**
     * 项目编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer itemId;
    /**
     * 项目名称
     */
    @NotBlank
    private String itemName;
    /**
     * 项目分值
     */
    @NotNull
    private Integer itemScore;
    /**
     * 所属细则
     */
    @NotNull
    private Integer detailRuleId;
    /**
     * 项目状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState itemState;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemScore() {
        return itemScore;
    }

    public void setItemScore(Integer itemScore) {
        this.itemScore = itemScore;
    }

    public Integer getDetailRuleId() {
        return detailRuleId;
    }

    public void setDetailRuleId(Integer detailRuleId) {
        this.detailRuleId = detailRuleId;
    }

    public IsEnableState getItemState() {
        return itemState;
    }

    public void setItemState(IsEnableState itemState) {
        this.itemState = itemState;
    }
}
