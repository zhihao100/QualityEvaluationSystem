package com.lzh.qes.service.impl;

import com.lzh.qes.dao.ManagerDao;
import com.lzh.qes.service.IManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lzh.qes.bean.Manager;
import com.lzh.qes.utils.PageUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuzhihao on 2017/4/7.
 */
@Service
public class ManagerService implements IManagerService {

    @Autowired
    private ManagerDao managerDao;


    @Override
    public Manager findManager() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = userDetails.getUsername();
        return managerDao.findByManagerName(name);
    }

    @Transactional
    @Override
    public String createManager(Manager manager) {
        /* 获取当前登录的管理员，并判断是否为超级管理员 */
        Manager superManager = findManager();
        if (superManager.isSuperManager()) {
            Manager exitedManager = managerDao.findByManagerName(manager.getManagerName());
            if (null == exitedManager) {
                manager.setRegisterDate(new Date());
                managerDao.save(manager);
                return "添加成功";
            }
            return "该管理员已存在";
        }
        return "添加失败,只有超级管理员可执行次操作";
    }


    @Transactional
    @Override
    public String updateManagerState(Manager manager) {
        /* 获取当前登录的管理员，并判断是否为超级管理员 */
        Manager superManager = findManager();
        if (superManager.isSuperManager()) {
            Manager exitedManager = managerDao.findByManagerId(manager.getManagerId());
            if (null != exitedManager) {
                exitedManager.setManagerState(manager.getManagerState());
                managerDao.save(exitedManager);
                return "修改成功";
            }
        }
        return "修改失败,只有超级管理员可执行次操作";
    }

    @Override
    public Page<Manager> findAllManagerByMultiConditionAndPage(PageUtils pageUtils) {
		/* 按管理员ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "managerId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        return managerDao.findAll(new Specification<Manager>() {

            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				/* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
    }

    /**
     * 拼接SQL
     *
     * @param root
     * @param builder
     * @param pageUtils
     * @return
     */
    private List<Predicate> createMultiConditionSQL(Root<Manager> root, CriteriaBuilder builder, PageUtils pageUtils) {
        Manager manager = pageUtils.getManager();
        List<Predicate> predicates = new ArrayList<Predicate>();
		/* 加入管理员状态 */
        if (null != manager.getManagerState()) {
            predicates.add(builder.equal(root.get("managerState"), manager.getManagerState()));
        }
		/* 加入管理员姓名，模糊查询 */
        if (StringUtils.isNotBlank(manager.getManagerName())) {
            predicates.add(builder.like(root.get("managerName"), "%" + manager.getManagerName() + "%"));
        }
        return predicates;
    }

    @Override
    public Manager showManagerDetails(long managerId) {
        return managerDao.findByManagerId(managerId);
    }

    @Transactional
    @Override
    public String updateManager(Manager manager) {
		/* 获取当前登录的管理员，并判断是否为超级管理员 */
        Manager superManager = findManager();
        if (superManager.isSuperManager()) {
            Manager exitedManager = managerDao.findByManagerId(manager.getManagerId());
            Manager exitedManagerName = managerDao.findByManagerName(manager.getManagerName());
            if (null == exitedManager) {
                return "该管理员不存在";
            }
            if (!exitedManager.getManagerName().equals(manager.getManagerName()) && null != exitedManagerName) {
                return "该管理员已存在";
            }
            exitedManager.setManagerName(manager.getManagerName());
            exitedManager.setPassword(manager.getPassword());
            exitedManager.setManagerState(manager.getManagerState());
            managerDao.save(manager);
            return "修改成功";
        }
        return "修改失败,只有超级管理员可执行次操作";
    }
}
