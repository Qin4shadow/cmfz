package com.baizhi.ql.controller;

import com.baizhi.ql.annotation.LogAnnotation;
import com.baizhi.ql.entity.Guru;
import com.baizhi.ql.service.GuruService;
import com.baizhi.ql.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    //分页查
    @LogAnnotation(value = "上师查询")
    @RequestMapping("/selectPageGuru")
    public Map selectPageAlbum(Integer page, Integer rows){
        Map map = guruService.selectPage(page, rows);
        return map;
    }
    //编辑判断是修改/删除/增加
    @RequestMapping("/saveGuru")
    public Map saveAlbum(Guru guru, String oper, String[] id){
        Map map  = null;
        if ("add".equals(oper)){
            map= guruService.insert(guru);
        } else if ("edit".equals(oper)){
            guru.setPhoto(null);
            map = guruService.update(guru);
        } else {
            guruService.delete(Arrays.asList(id));
        }
        return map;
    }
    //文件上传
    @RequestMapping("/uploadGuru")
    public Map uploadAlbum(MultipartFile photo, String guruId, HttpServletRequest request){

        String http = HttpUtil.getHttp(photo, request, "/upload/guruPhoto/");
        //将文件存放到指定目录
        Guru guru = new Guru();
        guru.setId(guruId);
        guru.setPhoto(http);
        guruService.update(guru);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }


    @RequestMapping("/selectAllGuru")
    public List<Guru> selectAllGuru(){
        List<Guru> gurus = guruService.selectAll();
        return gurus;
    }

    //17.展示上师列表
    @RequestMapping("/selectAll")
    public Map selectAll(String uid){
        List<Guru> gurus = guruService.selectAll();
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","ok");
        hashMap.put("list",gurus);
        return hashMap;
    }
    //18.添加关注上师
}
