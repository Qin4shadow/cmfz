package com.baizhi.ql.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.ql.annotation.LogAnnotation;
import com.baizhi.ql.dao.GuruDao;
import com.baizhi.ql.entity.Guru;
import com.baizhi.ql.entity.User;
import com.baizhi.ql.service.GuruService;
import com.baizhi.ql.service.UserService;
import com.baizhi.ql.util.HttpUtil;
import com.baizhi.ql.util.SmsUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    //根据性别时间查数量
    @RequestMapping("showUserTime")
    public Map showUserTime(){
        Map map = userService.showUserTime();
        return map;
    }

    //根据性比额查地区
    @RequestMapping("showMap")
    public Map showMap(){
        Map map = userService.showMap();
        return map;
    }


    //分页查
    @LogAnnotation(value = "用户查询")
    @RequestMapping("selectPageUser")
    public Map selectPageUser(Integer page,Integer rows){
        Map map = userService.selectPage(page, rows);
        return map;
    }
    //编辑判断是修改/删除/增加
    @RequestMapping("/saveUser")
    public Map saveUser(User user, String oper, String[] id){
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)){
            userService.insert(user);
            hashMap.put("userId",user.getId());
        } else if ("edit".equals(oper)){
            user.setPhoto(null);
            userService.update(user);
            hashMap.put("userId",user.getId());
        } else {
            userService.delete(Arrays.asList(id));
        }
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-6aeb906ba81c4dc693f78caa76fe418e");
        Map map = showUserTime();
        String s = JSONObject.toJSONString(map);
        goEasy.publish("cmfz", s);
        return hashMap;
    }

    //文件上传
    @RequestMapping("/uploadUser")
    public Map uploadUser(MultipartFile photo, String userId, HttpServletRequest request){

        String http = HttpUtil.getHttp(photo, request, "/upload/userImg/");
        //将文件存放到指定目录
        User user = new User();
        user.setId(userId);
        user.setPhoto(http);
        userService.update(user);

        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    //1.登陆接口
    @RequestMapping("/loginInterface")
    public Map loginInterface(String phone,String password){
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setPhone(phone);
        User user1 = userService.selectOne(user);
        if(user1==null){
            hashMap.put("status","-200");
            hashMap.put("message","账户不存在");
        }else if(user1.getPassword().equals(password)){
            hashMap.put("status","200");
            hashMap.put("user",user1);
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","密码不正确");
        }


        return hashMap;
    }
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //2.发送验证码
    @RequestMapping("/sendCode")
    public Map sendCode(String phone){
        HashMap hashMap = new HashMap();
        try {
            String s = UUID.randomUUID().toString();
            String substring = s.substring(0, 5);
            // SmsUtil.send(phone,substring);
            // 将验证码保存至Redis  Key phone_186XXXX Value code 1分钟有效
            stringRedisTemplate.opsForValue().set("phone_"+phone,substring,60, TimeUnit.SECONDS);

            hashMap.put("status","200");
            hashMap.put("message","短信发送成功");
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","短信发送失败");
        }
        return hashMap;
    }
    //3.注册接口
    @RequestMapping("/regist")
    public Map regist(String code,String phone){
        HashMap hashMap = new HashMap();
        //从redis中获取验证码
        String s = stringRedisTemplate.opsForValue().get("phone_" + phone);
        if(s==null){
            hashMap.put("status","-200");
            hashMap.put("message","验证码失效");
        }else if(code.equals(s)){
            hashMap.put("status","200");
            hashMap.put("message","注册成功");
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","注册失败");
        }

        return hashMap;
    }
    //4.补充个人信息接口
    @RequestMapping("/userMsg")
    public Map userMsg(User user){
        HashMap hashMap = new HashMap();
        try {
            userService.insert(user);
            hashMap.put("status","200");
            hashMap.put("user",user);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //15.修改个人信息
    @RequestMapping("/updateUser")
    public Map updateUser(User user){
        userService.update(user);
        User user1 = new User();
        user1.setId(user.getId());
        User user2 = userService.selectOne(user1);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("user",user2);
        return hashMap;
    }
    //16.金刚道友
    @RequestMapping("/selectFive")
    public Map selectFive(String uid){
        List<User> users = userService.selectFive();
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("list",users);
        return hashMap;
    }
    @Autowired
    GuruService guruService;
    //18.添加关注上师
    @RequestMapping("/focusGuru")
    public Map focusGuru(String uid,String id){
        stringRedisTemplate.opsForList().leftPush(uid,id);
        List<String> range = stringRedisTemplate.opsForList().range(uid, 0, -1);
        List<Guru> gurus = guruService.selectByIdList(range);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","添加成功");
        hashMap.put("list",gurus);
        return hashMap;
    }


}
