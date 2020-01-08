package com.baizhi.ql.service;

import com.baizhi.ql.dao.AdminDao;
import com.baizhi.ql.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Admin selectOne(Admin admin) {
        Admin admin1 = adminDao.selectOne(admin);
        return admin1;
    }
}
