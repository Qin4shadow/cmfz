package com.baizhi.ql.service;

import com.baizhi.ql.dao.GuruDao;
import com.baizhi.ql.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("guruService")
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Override
    public Map selectPage(Integer curPage, Integer pageSize) {
        HashMap hashMap = new HashMap();
        // 设置当前页 page
        hashMap.put("page",curPage);
        // 设置总行数 records
        int selectCount = guruDao.selectCount(null);
        hashMap.put("records",selectCount);
        // 设置总页 total
        hashMap.put("total",selectCount%pageSize==0? selectCount/pageSize:selectCount/pageSize+1);
        // 设置当前页的数据行 rows
        hashMap.put("rows",guruDao.selectByRowBounds(null,new RowBounds((curPage-1)*pageSize,pageSize)));
        return hashMap;
    }

    @Override
    public Map insert(Guru guru) {
        String s = UUID.randomUUID().toString();
        guru.setId(s);
        guruDao.insert(guru);
        HashMap hashMap = new HashMap();
        hashMap.put("guruId",guru.getId());
        return hashMap;
    }

    @Override
    public Map update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
        HashMap hashMap = new HashMap();
        hashMap.put("guruId",guru.getId());
        return hashMap;
    }

    @Override
    public void delete(List ids) {
        guruDao.deleteByIdList(ids);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> selectAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public List<Guru> selectByIdList(List ids) {
        List list = guruDao.selectByIdList(ids);
        return list;
    }
}
