package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Subject;
import com.zj.examsystem.entity.User;
import com.zj.examsystem.service.SubjectService;
import com.zj.examsystem.service.UserService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @GetMapping("/findAllByMajorId")
    @ResponseBody
    public Object findAllByMajorId(Integer pageno, Integer size, Integer majorId) {
        Map<String, Object> map = new HashMap<>();
        map.put("column", "major_id");
        map.put("val", majorId);
        return BaseResponseEntity.ok("", subjectService.findAll(pageno, size, map));
    }

    @GetMapping("/findAllByTeacherId")
    @ResponseBody
    public Object findAllByTeacherId(Integer pageno, Integer size, Integer teacherId) {
        Map<String, Object> map = new HashMap<>();
        map.put("column", "teacher_id");
        map.put("val", teacherId);
        return BaseResponseEntity.ok("", subjectService.findAll(pageno, size, map));
    }

    @GetMapping("/findAllSubId")
    @ResponseBody
    public Object findAllSubId() {
        return BaseResponseEntity.ok("", subjectService.loadSubjectByUserId(null));
    }

    @GetMapping("/loadSubjectByTeacherId")
    @ResponseBody
    public Object loadSubjectByTeacherId(Integer teacherId) {
        Map<String, Object> map = new HashMap<>();
        map.put("column", "teacher_id");
        map.put("val", teacherId);
        return BaseResponseEntity.ok("", subjectService.loadSubjectByUserId(map));
    }

    @GetMapping("/loadSubjectByStudentId")
    @ResponseBody
    public Object loadSubjectByStudentId(Integer studentId) {
        User student = userService.findById(studentId);
        Map<String, Object> map = new HashMap<>();
        map.put("column", "clazz_id");
        map.put("val", student.getClazzId());
        return BaseResponseEntity.ok("", subjectService.loadSubjectByUserId(map));
    }

    @GetMapping("/findById")
    @ResponseBody
    public Object findById(Integer subjectId) {
        return BaseResponseEntity.ok("", subjectService.findById(subjectId));
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(Subject subject, String status) {
        int result = subjectService.saveSubject(subject);
        return result != 0 ? BaseResponseEntity.ok(status + "成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer[] subjectId) {
        int result = subjectService.deleteSubject(subjectId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}

