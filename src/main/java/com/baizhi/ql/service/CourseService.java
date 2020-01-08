package com.baizhi.ql.service;

import com.baizhi.ql.entity.Course;

import java.util.List;

public interface CourseService {
    //展示
    List<Course> selectByUid(String uid);
    //添加
    void insert(String uid,String title);
    //删除
    void delete(String id,String uid);
}
