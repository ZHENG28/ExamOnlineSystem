package com.zj.examsystem.controller;

import com.zj.examsystem.entity.Major;
import com.zj.examsystem.service.MajorService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/major")
public class MajorController {
    @Resource
    private MajorService majorService;

    @GetMapping("/findAll")
    @ResponseBody
    public BaseResponseEntity<Object> findAll(Integer pageno, Integer size) {
        return BaseResponseEntity.ok("", majorService.findAll(pageno, size));
    }

    @GetMapping("/findById")
    @ResponseBody
    public BaseResponseEntity<Major> findById(Integer majorId) {
        return BaseResponseEntity.ok("", majorService.findById(majorId));
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponseEntity<Integer> save(Major major, String status) {
        int result = majorService.saveMajor(major);
        return result != 0 ? BaseResponseEntity.ok(status + "成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public BaseResponseEntity<Integer> delete(Integer[] majorId) {
        int result = majorService.deleteMajor(majorId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}

