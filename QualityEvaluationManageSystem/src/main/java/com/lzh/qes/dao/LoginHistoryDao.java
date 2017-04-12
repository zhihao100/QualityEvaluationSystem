package com.lzh.qes.dao;

import com.lzh.qes.bean.LoginHistory;

import java.util.Calendar;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 登陆历史接口
 * Created by liuzhihao on 2017/4/12.
 */
public interface LoginHistoryDao extends CrudRepository<LoginHistory, Long> {

    /**
     * 每天凌晨清除一个月之前的登录历史记录
     */
    @Query(value = "delete from login_history where login_date<?1", nativeQuery = true)
    @Modifying
    @Transactional
    void clearLoginHistory(Calendar calendar);

}
