package com.lzh.qes.modal.vo;

import com.lzh.qes.bean.ClassManage;

import java.io.Serializable;

/**
 * Created by 新乐 on 2017/4/13.
 */
public class ClassManageVO implements Serializable {
    /**
     * 班级实体
     */
    private ClassManage classManage;
    /**
     * 班级总数
     */
    private Long totalClass;
    /**
     * 所属学院名称
     */
    private String instituteName;
    /**
     * 所属专业名称
     */
    private String majorName;

    public ClassManage getClassManage() {
        return classManage;
    }

    public void setClassManage(ClassManage classManage) {
        this.classManage = classManage;
    }

    public Long getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(Long totalClass) {
        this.totalClass = totalClass;
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
}
