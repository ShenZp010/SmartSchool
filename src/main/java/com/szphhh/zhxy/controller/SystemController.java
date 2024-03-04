package com.szphhh.zhxy.controller;


import com.szphhh.zhxy.util.CreateVerifiCodeImage;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

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
