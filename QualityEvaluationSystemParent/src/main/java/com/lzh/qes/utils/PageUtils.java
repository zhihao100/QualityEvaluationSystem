package com.lzh.qes.utils;

import com.lzh.qes.bean.ClassManage;
import com.lzh.qes.bean.Institute;
import com.lzh.qes.bean.Manager;

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
}
