package com.baizhi.ql.controller;

import com.baizhi.ql.entity.Admin;
import com.baizhi.ql.service.AdminService;
import com.baizhi.ql.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    // 生成验证码图片
    @RequestMapping("/createImg")
    public void createImg(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode(); // 随机验证码
        vcode.write(response.getOutputStream()); // 把图片输出client

        // 把生成的验证码 存入session
        session.setAttribute("ServerCode", code);
    }

    //登陆
    @RequestMapping("/login")
    @ResponseBody
    public Map login(HttpSession session, String username, String password, String code) {
        Admin admin1 = adminService.selectOne(new Admin(null, username, null));
        HashMap hashMap = new HashMap();
        if (code.equals(session.getAttribute("ServerCode"))) {
            if (admin1 != null) {
                if (password.equals(admin1.getPassword())) {
                    hashMap.put("status",200);
                    session.setAttribute("admin", admin1);
                } else {
                    hashMap.put("status",400);
                    hashMap.put("msg","密码不正确");
                }
            } else {
                hashMap.put("status",400);
                hashMap.put("msg","用户不存在");
            }
        } else {
            hashMap.put("status",400);
            hashMap.put("msg","验证码不正确");
        }
        return hashMap;
    }

    //退出登陆
    @RequestMapping("/logout")
    public void logout(HttpSession session){
        session.notifyAll();
    }

}