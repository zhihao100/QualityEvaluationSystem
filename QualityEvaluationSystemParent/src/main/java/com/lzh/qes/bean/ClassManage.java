package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
     * 班级序号
     */
    @NotNull
    private  Integer classNumber;
    /**
     * 班级状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState classState;
    /**
     * 所属学院
     */
    @NotNull
    private Integer instituteId;
    /**
     * 所属专业
     */
    @NotNull
    private Integer majorId;
    /**
     * 所在年级
     */
    @NotNull
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

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }
}
