package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by liuzhihao on 2017/4/12.
 */
@Entity
public class ClassManage {
    /**
     * 班级ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classId;
    /**
     * 班级名称（全称）
     */
    @NotEmpty
    private String classFullName;
    /**
     * 班级名称（简称）
     */
    @NotEmpty
    private String classShortName;
    /**
     * 班级状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState classState;
    /**
     * 所属学院
     */
    @NotEmpty
    private Integer instituteId;
    /**
     * 所属专业
     */
    @NotEmpty
    private String major;
    /**
     * 所在年级
     */
    @NotEmpty
    private Integer grade;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getClassShortName() {
        return classShortName;
    }

    public void setClassShortName(String classShortName) {
        this.classShortName = classShortName;
    }

    public IsEnableState getClassState() {
        return classState;
    }

    public void setClassState(IsEnableState classState) {
        this.classState = classState;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
