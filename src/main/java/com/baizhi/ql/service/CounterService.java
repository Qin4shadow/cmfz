package com.baizhi.ql.service;

import com.baizhi.ql.entity.Counter;

import java.util.List;

public interface CounterService {
    //展示
    List<Counter> selectByUid(String uid,String cid);
    //添加
    void insert(String uid,String title,String cid);
    //删除
    void delete(String id,String uid,String cid);
    //表更计数器
    void update(String uid,String id,long count,String cid);
}
