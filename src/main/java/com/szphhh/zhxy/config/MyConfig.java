package com.szphhh.zhxy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.szphhh.zhxy.mapper")
public class MyConfig {

    /**
     * 分页插件
     */
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置每页条目数量
        //paginationInterceptor.setLimit(10);
        return paginationInterceptor;
    }
}
