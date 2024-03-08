package com.szphhh.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szphhh.zhxy.pojo.Grade;
import com.szphhh.zhxy.service.GradeService;
import com.szphhh.zhxy.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@PathVariable("pageNo") Integer pageNo,@PathVariable("pageSize") Integer pageSize,String gradeName){
        //分页 带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //通过服务层
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page,gradeName);
        //封装Result对象并返回
        return Result.ok(pageRs);
    }
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody Integer[] ids){
        gradeService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
