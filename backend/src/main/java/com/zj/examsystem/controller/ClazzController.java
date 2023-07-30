package com.zj.examsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zj.examsystem.entity.Clazz;
import com.zj.examsystem.service.ClazzService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clazz")
public class ClazzController {
    @Resource
    private ClazzService clazzService;

    @GetMapping("/findAll")
    @ResponseBody
    public BaseResponseEntity<IPage<Clazz>> findAll(Integer pageno, Integer size) {
        return BaseResponseEntity.ok("", clazzService.findAll(pageno, size));
    }

    @GetMapping("/findById")
    @ResponseBody
    public BaseResponseEntity<Clazz> findById(Integer clazzId) {
        return BaseResponseEntity.ok("", clazzService.findById(clazzId));
    }

    @GetMapping("/loadMajorClazzCascader")
    @ResponseBody
    public BaseResponseEntity<List<Map<String, Object>>> loadMajorClazzCascader() {
        return BaseResponseEntity.ok("", clazzService.findMajorAndClazzList());
    }

    @GetMapping("/loadClazzByMajorId")
    @ResponseBody
    public BaseResponseEntity<List<Clazz>> loadClazzByMajorId(Integer majorId) {
        return BaseResponseEntity.ok("", clazzService.loadClazzByMajorId(majorId));
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponseEntity<Integer> save(Clazz clazz, String status) {
        int result = clazzService.saveClazz(clazz);
        return result != 0 ? BaseResponseEntity.ok(status + "成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public BaseResponseEntity<Integer> delete(Integer[] clazzId) {
        int result = clazzService.deleteClazz(clazzId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}