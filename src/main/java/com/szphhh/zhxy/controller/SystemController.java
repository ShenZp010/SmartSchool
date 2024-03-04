package com.szphhh.zhxy.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.szphhh.zhxy.pojo.Admin;
import com.szphhh.zhxy.pojo.LoginForm;
import com.szphhh.zhxy.pojo.Student;
import com.szphhh.zhxy.pojo.Teacher;
import com.szphhh.zhxy.service.AdminService;
import com.szphhh.zhxy.service.StudentService;
import com.szphhh.zhxy.service.TeacherService;
import com.szphhh.zhxy.util.CreateVerifiCodeImage;
import com.szphhh.zhxy.util.JwtHelper;
import com.szphhh.zhxy.util.Result;
import com.szphhh.zhxy.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        //token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request,HttpServletResponse response){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode().toLowerCase();
        sessionVerifiCode = sessionVerifiCode.toLowerCase();
        if("".equals(sessionVerifiCode)|| null == sessionVerifiCode){
            return Result.fail().message("验证码失效");
        }
        if(!sessionVerifiCode.equals(loginVerifiCode)){
            return Result.fail().message("验证码错误");
        }
        //从session中移除验证码
        session.removeAttribute("verifiCode");
        //分用户类型进行校验   准备一个map存放响应的数据
        Map<String,Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if(admin!=null){
                        //用户的类型和id转换成Token并反馈
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if(student!=null){
                        //用户的类型和id转换成Token并反馈
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if(teacher!=null){
                        //用户的类型和id转换成Token并反馈
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("用户不存在");
    }

    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        // 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        System.out.println("验证码：【"+verifiCode+"】");
        //将验证码文本放入session域为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG", response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
