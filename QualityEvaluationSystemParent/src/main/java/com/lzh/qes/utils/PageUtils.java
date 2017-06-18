package com.lzh.qes.utils;

import com.lzh.qes.bean.*;

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
     * 细则
     */
    private DetailRule detailRule;
    /**
     * 申请
     */
    private Application application;


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

    public DetailRule getDetailRule() {
        return detailRule;
    }

    public void setDetailRule(DetailRule detailRule) {
        this.detailRule = detailRule;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
