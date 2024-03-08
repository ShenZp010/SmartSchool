package com.szphhh.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szphhh.zhxy.mapper.GradeMapper;
import com.szphhh.zhxy.pojo.Grade;
import com.szphhh.zhxy.service.GradeService;
import com.szphhh.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName) {
        LambdaQueryWrapper<Grade> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like(Grade::getName,gradeName);
        }
        queryWrapper.orderByDesc(Grade::getId);

        Page<Grade> page = gradeMapper.selectPage(pageParam, queryWrapper);
        return page;
    }

}
