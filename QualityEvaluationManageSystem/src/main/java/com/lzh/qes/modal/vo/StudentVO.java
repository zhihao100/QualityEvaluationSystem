package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.Student;

import java.io.Serializable;

/**
 * Created by liuzhihao on 2017/4/16.
 */
public class StudentVO implements Serializable {
    /**
     * 学生实体
     */
    private Student student;
    /**
     * 所属学院名称
     */
    private String instituteName;
    /**
     * 所属专业名称
     */
    private String majorName;
    /**
     * 所属班级名称（简称）
     */
    private String classShortName;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getClassShortName() {
        return classShortName;
    }

    public void setClassShortName(String classShortName) {
        this.classShortName = classShortName;
    }
}
