package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Clazz;
import com.zj.examsystem.service.ClazzService;
import com.zj.examsystem.utils.response.ResponseCode;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/clazz")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/findAll")
    @ResponseBody
    public Object findAll(Integer pageno, Integer size) {
        return BaseResponseEntity.ok("", clazzService.findAll(pageno, size));
    }

    @GetMapping("/findById")
    @ResponseBody
    public Object findById(Integer clazzId) {
        return BaseResponseEntity.ok("", clazzService.findById(clazzId));
    }

    @GetMapping("/loadMajorClazzCascader")
    @ResponseBody
    public Object loadMajorClazzCascader() {
        return BaseResponseEntity.ok("", clazzService.findMajorAndClazzList());
    }

    @GetMapping("/loadClazzByMajorId")
    @ResponseBody
    public Object loadClazzByMajorId(Integer majorId) {
        return BaseResponseEntity.ok("", clazzService.loadClazzByMajorId(majorId));
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(Clazz clazz, String status) {
        int result = clazzService.saveClazz(clazz);
        return result != 0 ? BaseResponseEntity.ok(status + "成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer[] clazzId) {
        int result = clazzService.deleteClazz(clazzId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}