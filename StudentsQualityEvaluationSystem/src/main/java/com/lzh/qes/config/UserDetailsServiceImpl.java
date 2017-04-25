package com.lzh.qes.config;


import com.lzh.qes.bean.Student;
import com.lzh.qes.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by liuzhihao on 2017/4/25.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    /**
     * 通过学生学号查找用户
     */
    @Override
    public UserDetails loadUserByUsername(String stuNumber) throws UsernameNotFoundException {
        Student user = userDao.findByStudentNumber(stuNumber);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER" + stuNumber));
        return new org.springframework.security.core.userdetails.User(user.getStudentNumber(), user.getStudentPassword(),
                authorities);
    }

}
