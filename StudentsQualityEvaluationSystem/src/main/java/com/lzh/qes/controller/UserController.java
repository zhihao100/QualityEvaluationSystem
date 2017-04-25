package com.lzh.qes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuzhihao on 2017/4/25.
 */
@Controller
public class UserController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 素测学生登陆
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        LOGGER.info("素质测评学生登录");
        return "login";
    }

    /**
     * 访问主页面
     *
     * @return
     */
    @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
    public String index() {
        LOGGER.info("访问主页面");
        return "index";
    }
}
