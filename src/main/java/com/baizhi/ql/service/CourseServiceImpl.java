package com.baizhi.ql.service;

import com.baizhi.ql.dao.CourseDao;
import com.baizhi.ql.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Course> selectByUid(String uid) {
        Course course = new Course();
        course.setUserId(uid);
        List<Course> select = courseDao.select(course);
        return select;
    }

    @Override
    public void insert(String uid, String title) {
        Course course = new Course();
        course.setUserId(uid);
        course.setTitle(title);
        course.setId(UUID.randomUUID().toString());
        course.setCreateDate(new Date());
        courseDao.insertSelective(course);
    }

    @Override
    public void delete(String id, String uid) {
        Course course = new Course();
        course.setUserId(uid);
        course.setId(id);
        courseDao.delete(course);
    }
}
