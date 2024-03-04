package com.szphhh.zhxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.szphhh.zhxy.pojo.Admin;
import com.szphhh.zhxy.pojo.LoginForm;

public interface AdminService extends IService<Admin> {


    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);
}
