package com.baizhi.ql.service;

import com.baizhi.ql.dao.AlbumDao;
import com.baizhi.ql.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map selectPage(Integer curPage, Integer pageSize) {
        HashMap hashMap = new HashMap();
        // 设置当前页 page
        hashMap.put("page",curPage);
        // 设置总行数 records
        int selectCount = albumDao.selectCount(null);
        hashMap.put("records",selectCount);
        // 设置总页 total
        hashMap.put("total",selectCount%pageSize==0? selectCount/pageSize:selectCount/pageSize+1);
        // 设置当前页的数据行 rows
        hashMap.put("rows",albumDao.selectByRowBounds(null,new RowBounds((curPage-1)*pageSize,pageSize)));
        return hashMap;
    }

    @Override
    public Map insert(Album album) {
        album.setId(UUID.randomUUID().toString());
        albumDao.insert(album);
        HashMap hashMap = new HashMap();
        hashMap.put("albumId",album.getId());
        return hashMap;
    }

    @Override
    public Map update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
        HashMap hashMap = new HashMap();
        hashMap.put("albumId",album.getId());
        return hashMap;
    }

    @Override
    public void delete(List ids) {
        albumDao.deleteByIdList(ids);
    }

    @Override
    public List<Album> selectByRowBounds() {
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
        return albums;
    }

    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
}
