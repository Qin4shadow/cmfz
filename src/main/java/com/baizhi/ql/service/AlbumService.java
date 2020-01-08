package com.baizhi.ql.service;


import com.baizhi.ql.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //分页查
    Map selectPage(Integer curPage, Integer pageSize);
    //添加
    Map insert(Album album);
    //修改
    Map update(Album album);
    //删除
    void delete(List ids);
    //分页查6条数据
    List<Album> selectByRowBounds();
    //根据id查专辑
    Album selectOne(String id);
}
