package com.lzh.qes.controller;

import com.lzh.qes.bean.Student;
import com.lzh.qes.modal.vo.StudentVO;
import com.lzh.qes.search.PageList;
import com.lzh.qes.service.IStudentManageService;
import com.lzh.qes.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzhihao on 2017/4/16.
 */
@Controller
public class StudentController {
    private static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private IStudentManageService iStudentManageService;

    /**
     * 多条件分页查询学生
     *
     * @param pageUtils
     * @return
     */
    @RequestMapping(value = "findAllStudentByMultiConditionAndPage", method = RequestMethod.POST)
    @ResponseBody
    public PageList<StudentVO> findAllStudentByMultiConditionAndPage(@RequestBody PageUtils pageUtils) {
        LOGGER.info("多条件分页查询学生");
        Assert.notNull(pageUtils, "分页工具为空");
        if (null == pageUtils.getStudent()) {
            Student student = new Student();
            pageUtils.setStudent(student);
        }
        PageList<StudentVO> students = iStudentManageService.findAllStudentByMultiConditionAndPage(pageUtils);
        LOGGER.info("学生数据条数" + students.getPagersInfo().get("totalElements"));
        return students;
    }
    /**
     * 学生详情
     *
     * @param studentId
     * @return
     */
    @RequestMapping(value = "showStudentDetails", method = RequestMethod.POST)
    @ResponseBody
    public StudentVO showStudentDetails(@RequestBody Long studentId) {
        LOGGER.info("班级详情");
        Assert.notNull(studentId);
        return iStudentManageService.showStudentDetails(studentId);
    }
    /**
     * 添加学生
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "createStudent", method = RequestMethod.POST)
    @ResponseBody
    public String createStudent(@RequestBody Student student) {
        LOGGER.info("添加学生");
        Assert.notNull(student);
        return iStudentManageService.createStudent(student);
    }
    /**
     * 修改学生
     * @param student
     * @return
     */
    @RequestMapping(value = "updateStudent", method = RequestMethod.POST)
    @ResponseBody
    public String updateClass(@RequestBody Student student) {
        LOGGER.info("修改学生");
        Assert.notNull(student, "学生不存在");
        return iStudentManageService.updateStudent(student);
    }
}
