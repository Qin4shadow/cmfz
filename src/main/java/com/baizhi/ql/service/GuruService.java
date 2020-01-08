package com.baizhi.ql.service;

import com.baizhi.ql.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //分页查
    Map selectPage(Integer curPage, Integer pageSize);
    //添加
    Map insert(Guru guru);
    //修改
    Map update(Guru guru);
    //删除
    void delete(List ids);
    //查所有
    List<Guru> selectAll();
    //id集合查所有
    List<Guru> selectByIdList(List ids);
}
