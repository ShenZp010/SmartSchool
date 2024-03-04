package com.szphhh.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szphhh.zhxy.mapper.StudentMapper;
import com.szphhh.zhxy.pojo.Admin;
import com.szphhh.zhxy.pojo.LoginForm;
import com.szphhh.zhxy.pojo.Student;
import com.szphhh.zhxy.service.StudentService;
import com.szphhh.zhxy.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**

 */
@Service("stuService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(LoginForm loginForm) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getName,loginForm.getUsername());
        queryWrapper.eq(Student::getPassword, MD5.encrypt(loginForm.getPassword()));
        Student student = studentMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getId,userId);
        Student student = studentMapper.selectOne(queryWrapper);
        return student;
    }
}
