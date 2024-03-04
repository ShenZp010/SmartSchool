package com.szphhh.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szphhh.zhxy.pojo.LoginForm;
import com.szphhh.zhxy.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {


    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);
}
