package com.szphhh.zhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szphhh.zhxy.mapper.AdminMapper;
import com.szphhh.zhxy.pojo.Admin;
import com.szphhh.zhxy.pojo.LoginForm;
import com.szphhh.zhxy.service.AdminService;
import com.szphhh.zhxy.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(LoginForm loginForm) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getName,loginForm.getUsername());
        queryWrapper.eq(Admin::getPassword, MD5.encrypt(loginForm.getPassword()));
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getId,userId);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }
}
