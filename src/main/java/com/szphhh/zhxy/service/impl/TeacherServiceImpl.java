package com.szphhh.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szphhh.zhxy.mapper.TeacherMapper;
import com.szphhh.zhxy.pojo.Admin;
import com.szphhh.zhxy.pojo.LoginForm;
import com.szphhh.zhxy.pojo.Student;
import com.szphhh.zhxy.pojo.Teacher;
import com.szphhh.zhxy.service.TeacherService;
import com.szphhh.zhxy.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("teaService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(LoginForm loginForm) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getName,loginForm.getUsername());
        queryWrapper.eq(Teacher::getPassword, MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getId,userId);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        return teacher;
    }
}
