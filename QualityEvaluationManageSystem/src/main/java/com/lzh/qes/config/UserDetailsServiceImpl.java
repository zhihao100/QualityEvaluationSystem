package com.lzh.qes.config;

import com.lzh.qes.dao.ManagerDao;
import com.lzh.qes.bean.Manager;
import com.lzh.qes.enums.IsEnableState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by liuzhihao on 2017/4/7.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ManagerDao managerDao;

    /**
     * 通过名字查找用户,赋予管理员权限角色
     */
    @Override
    public UserDetails loadUserByUsername(String managername) throws UsernameNotFoundException {
        Manager manager = managerDao.findByManagerName(managername);
        if (manager.getManagerState().equals(IsEnableState.停用)) {
            return null;
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER" + managername));
        return new org.springframework.security.core.userdetails.User(manager.getManagerName(), manager.getPassword(),
                authorities);
    }

}
