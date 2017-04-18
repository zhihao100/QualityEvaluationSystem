package com.lzh.qes.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by liuzhihao on 2017/4/16.
 */
@Entity
public class Student {
    /**
     * 学生ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;
    /**
     * 学生名称
     */
    @NotBlank
    private String studentName;
    /**
     * 学生学号
     */
    @NotNull
    private String studentNumber;
    /**
     * 学生性别
     */
    @NotNull
    private Integer gender;
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
    /**
     * 所属班级
     */
    @NotNull
    private Integer classId;

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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
