package com.baizhi.ql.controller;

import com.baizhi.ql.entity.Album;
import com.baizhi.ql.entity.Article;
import com.baizhi.ql.entity.Banner;
import com.baizhi.ql.service.AlbumService;
import com.baizhi.ql.service.ArticleService;
import com.baizhi.ql.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/onePage")
public class OnePageController {
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @RequestMapping("/onePage")
    // type : all|wen|si
    public Map onePage(String uid, String type, String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerService.queryBannersByTime();
                List<Album> albums = albumService.selectByRowBounds();
                List<Article> articles = articleService.selectAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumService.selectByRowBounds();
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> articles = articleService.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleService.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }

        return hashMap;
    }
}
