package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by liuzhihao on 2017/4/13.
 */
@Entity
public class Major {
    /**
     * 专业ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer majorId;
    /**
     * 专业名称
     */
    @NotEmpty
    private String majorName;
    /**
     * 专业状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState majorState;
    /**
     * 所属学院
     */
    @NotNull
    private Integer instituteId;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public IsEnableState getMajorState() {
        return majorState;
    }

    public void setMajorState(IsEnableState majorState) {
        this.majorState = majorState;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }
}
