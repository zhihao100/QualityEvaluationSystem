package com.lzh.qes.bean;

import com.lzh.qes.enums.ApplicationState;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by liuzhihao on 2017/5/15.
 */
@Entity
public class Application {
    /**
     * 申请ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationId;
    /**
     * 申请人
     */
    @NotNull
    private Long studentId;
    /**
     * 申请姓名
     */
    @NotBlank
    private String studentName;
    /**
     * 申请人所属学院
     */
    @NotNull
    private Integer instituteId;
    /**
     * 申请所属项目
     */
    @NotNull
    private Integer itemRuleId;
    /**
     * 申请项目所属细则
     */
    @NotNull
    private Integer detailRuleId;
    /**
     * 申请项目所属大类
     */
    @NotNull
    private Integer mainRuleId;
    /**
     * 申请状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private ApplicationState applicationState;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public Integer getItemRuleId() {
        return itemRuleId;
    }

    public void setItemRuleId(Integer itemRuleId) {
        this.itemRuleId = itemRuleId;
    }

    public Integer getDetailRuleId() {
        return detailRuleId;
    }

    public void setDetailRuleId(Integer detailRuleId) {
        this.detailRuleId = detailRuleId;
    }

    public Integer getMainRuleId() {
        return mainRuleId;
    }

    public void setMainRuleId(Integer mainRuleId) {
        this.mainRuleId = mainRuleId;
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }
}
