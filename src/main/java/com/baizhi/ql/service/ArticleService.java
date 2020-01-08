package com.baizhi.ql.service;


import com.baizhi.ql.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查
    Map selectPage(Integer curPage, Integer pageSize);
    //添加
    Map insert(Article article);
    //修改
    void update(Article article);
    //删除
    void delete(List ids);
    //查所有
    List<Article> selectAll();
    //根据id查一个
    Article selectOne(String id);
}
