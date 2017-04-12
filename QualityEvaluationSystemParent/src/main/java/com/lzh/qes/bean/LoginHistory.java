package com.lzh.qes.bean;

import com.lzh.qes.enums.LoginPerson;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录历史
 * Created by liuzhihao on 2017/4/12.
 */
@Entity
public class LoginHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录历史ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loginHistoryId;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登录时间
     */
    private Date loginDate;

    /**
     * 登录人（用户，管理员）
     */
    @Autowired
    @Enumerated(EnumType.ORDINAL)
    private LoginPerson loginPerson;

    /**
     * 登录人ID
     */
    private long businessId;

    public long getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(long loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public LoginPerson getLoginPerson() {
        return loginPerson;
    }

    public void setLoginPerson(LoginPerson loginPerson) {
        this.loginPerson = loginPerson;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

