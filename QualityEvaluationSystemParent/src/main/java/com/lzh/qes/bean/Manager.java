package com.lzh.qes.bean;

import com.lzh.qes.enums.IsEnableState;
import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理员实体
 * Created by liuzhihao on 2017/4/7.
 */
@Entity
public class Manager implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 管理员ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long managerId;
    /**
     * 管理员名称
     */
    @NotEmpty
    private String managerName;
    /**
     * 管理员密码
     */
    @NotEmpty
    private String password;

    /**
     * 管理员状态
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private IsEnableState managerState;

    /**
     * 是否为超级管理员
     */
    private boolean isSuperManager;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 所属学院
     */
    @NotNull
    private Integer instituteId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IsEnableState getManagerState() {
        return managerState;
    }

    public void setManagerState(IsEnableState managerState) {
        this.managerState = managerState;
    }

    public boolean isSuperManager() {
        return isSuperManager;
    }

    public void setSuperManager(boolean superManager) {
        isSuperManager = superManager;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }
}
