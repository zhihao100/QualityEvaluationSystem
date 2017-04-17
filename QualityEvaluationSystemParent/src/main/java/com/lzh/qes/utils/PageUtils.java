package com.lzh.qes.utils;

import com.lzh.qes.bean.*;

import java.util.Date;

/**
 * Created by liuzhihao on 2017/4/7.
 */
public class PageUtils {
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页数据条数
     */
    private int pageSize;

    /**
     * 管理员
     */
    private Manager manager;
    /**
     * 学院
     */
    private Institute institute;
    /**
     * 班级
     */
    private ClassManage classManage;
    /**
     * 学生
     */
    private Student student;
    /**
     * 专业
     */
    private Major major;
    /**
     * 细则大类
     */
    private MainRule mainRule;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public ClassManage getClassManage() {
        return classManage;
    }

    public void setClassManage(ClassManage classManage) {
        this.classManage = classManage;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public MainRule getMainRule() {
        return mainRule;
    }

    public void setMainRule(MainRule mainRule) {
        this.mainRule = mainRule;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
