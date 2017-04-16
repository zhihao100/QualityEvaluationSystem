package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 学院实体类
 * Created by liuzhihao on 2017/4/11.
 */
@Entity
public class Institute implements Serializable {
    /**
     * 学院ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int instituteId;
    /**
     * 学院名称
     */
    @NotBlank
    private String instituteName;
    /**
     * 学院状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState instituteState;

    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public IsEnableState getInstituteState() {
        return instituteState;
    }

    public void setInstituteState(IsEnableState instituteState) {
        this.instituteState = instituteState;
    }
}
