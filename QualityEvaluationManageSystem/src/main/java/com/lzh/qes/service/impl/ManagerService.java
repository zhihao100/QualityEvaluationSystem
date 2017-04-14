package com.lzh.qes.service.impl;

import com.lzh.qes.bean.LoginHistory;
import com.lzh.qes.bean.Manager;
import com.lzh.qes.dao.LoginHistoryDao;
import com.lzh.qes.dao.ManagerDao;
import com.lzh.qes.enums.LoginPerson;
import com.lzh.qes.modal.vo.ManagerVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IInstituteManageService;
import com.lzh.qes.service.IManagerService;
import com.lzh.qes.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by liuzhihao on 2017/4/7.
 */
@Service
public class ManagerService implements IManagerService {

    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private LoginHistoryDao loginHistoryDao;
    @Autowired
    private IInstituteManageService iInstituteManageService;


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
            if (null == manager.getInstituteId()) {
                return "请选择学院再添加";
            }
            Manager existedManager = managerDao.findByManagerName(manager.getManagerName());
            if (null == existedManager) {
                manager.setRegisterDate(new Date());
                managerDao.save(manager);
                return "添加成功";
            }
            return "该管理员已存在";
        }
        return "添加失败,只有超级管理员可执行此操作";
    }


    @Transactional
    @Override
    public String updateManagerState(Manager manager) {
        /* 获取当前登录的管理员，并判断是否为超级管理员 */
        Manager superManager = findManager();
        if (superManager.isSuperManager()) {
            Manager existedManager = managerDao.findByManagerId(manager.getManagerId());
            if (null != existedManager) {
                existedManager.setManagerState(manager.getManagerState());
                managerDao.save(existedManager);
                return "修改成功";
            }
        }
        return "修改失败,只有超级管理员可执行次操作";
    }

    @Override
    public PageList<ManagerVO> findAllManagerByMultiConditionAndPage(PageUtils pageUtils) {
        /* 按管理员ID升序排列 */
        Sort sort = new Sort(Sort.Direction.ASC, "managerId");
        PageRequest pageRequest = new PageRequest(pageUtils.getCurrentPage() - 1, pageUtils.getPageSize(), sort);

        Page<Manager> managerPage = managerDao.findAll(new Specification<Manager>() {

            @Override
            public Predicate toPredicate(Root<Manager> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				/* 拼接查询语句 */
                List<Predicate> predicates = createMultiConditionSQL(root, builder, pageUtils);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageRequest);
        List<ManagerVO> voList = new ArrayList<>();
        PageList<ManagerVO> voPageList = new PageList<>();
        for (Manager manager : managerPage.getContent()) {
            ManagerVO managerVO = new ManagerVO();
            if (manager != null && manager.getInstituteId() != null) {
                managerVO.setInstituteName(iInstituteManageService.showInstituteDetails(manager.getInstituteId()).getInstituteName());
                managerVO.setManager(manager);
            }
            voList.add(managerVO);
        }
        voPageList.setDataList(voList);
        Map<String, Long> map = new HashMap<>();
        map.put("totalElements", managerPage.getTotalElements());
        voPageList.setPagersInfo(map);
        return voPageList;
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
    public ManagerVO showManagerDetails(long managerId) {
        Manager manager = managerDao.findByManagerId(managerId);
        ManagerVO managerVO = new ManagerVO();
        managerVO.setInstituteName(iInstituteManageService.showInstituteDetails(manager.getInstituteId()).getInstituteName());
        managerVO.setManager(manager);
        return managerVO;
    }

    @Transactional
    @Override
    public String updateManager(Manager manager) {
		/* 获取当前登录的管理员，并判断是否为超级管理员 */
        Manager superManager = findManager();
        if (superManager.isSuperManager()) {
            Manager existedManager = managerDao.findByManagerId(manager.getManagerId());
            Manager existedManagerName = managerDao.findByManagerName(manager.getManagerName());
            if (null == existedManager) {
                return "该管理员不存在";
            }
            if (null == manager.getInstituteId()) {
                return "请选择学院";
            }
            if (!existedManager.getManagerName().equals(manager.getManagerName()) && null != existedManagerName) {
                return "该管理员已存在";
            }
            existedManager.setManagerName(manager.getManagerName());
            existedManager.setPassword(manager.getPassword());
            existedManager.setManagerState(manager.getManagerState());
            existedManager.setInstituteId(manager.getInstituteId());
            managerDao.save(manager);
            return "修改成功";
        }
        return "修改失败,只有超级管理员可执行此操作";
    }

    @Transactional
    @Override
    public void createLoginHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) auth.getDetails();
        String name = userDetails.getUsername();
        Manager existedManager = managerDao.findByManagerName(name);
        if (null != existedManager) {
            LoginHistory loginHistory = new LoginHistory();
            /* 登陆时间 */
            loginHistory.setLoginDate(new Date());
			/* 登录人类型 */
            loginHistory.setLoginPerson(LoginPerson.管理员);
			/* 登录人ID */
            loginHistory.setBusinessId(existedManager.getManagerId());
            /* 登录人IP */
            loginHistory.setIp(webAuthenticationDetails.getRemoteAddress());
            loginHistoryDao.save(loginHistory);
			/* 更新最后登录时间 */
            existedManager.setLastLoginDate(new Date());
            managerDao.save(existedManager);
        }
    }

}
