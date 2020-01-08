package com.baizhi.ql.service;

import com.baizhi.ql.dao.AlbumDao;
import com.baizhi.ql.dao.ChapterDao;
import com.baizhi.ql.entity.Album;
import com.baizhi.ql.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map selectPage(Integer curPage, Integer pageSize,String albumId) {
        HashMap hashMap = new HashMap();
        // 设置当前页 page
        hashMap.put("page",curPage);
        // 设置总行数 records
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        int selectCount = chapterDao.selectCount(chapter);
        hashMap.put("records",selectCount);
        // 设置总页 total
        hashMap.put("total",selectCount%pageSize==0? selectCount/pageSize:selectCount/pageSize+1);
        // 设置当前页的数据行 rows
        hashMap.put("rows",chapterDao.selectByRowBounds(chapter,new RowBounds((curPage-1)*pageSize,pageSize)));
        return hashMap;
    }

    @Override
    public Map insert(Chapter chapter,String albumId) {
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insert(chapter);
        HashMap hashMap = new HashMap();
        //修改专辑中的章节数
        Album album = albumDao.selectByPrimaryKey(albumId);
        album.setCount(album.getCount()+1);
        albumDao.updateByPrimaryKeySelective(album);
        hashMap.put("chapterId",chapter.getId());
        return hashMap;
    }

    @Override
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public void delete(List ids,String albumId) {
        chapterDao.deleteByIdList(ids);
        //修改专辑中的章节数
        Album album = albumDao.selectByPrimaryKey(albumId);
        if(album.getCount()>0){
            album.setCount(album.getCount()-1);
            albumDao.updateByPrimaryKeySelective(album);
        }
    }

    @Override
    public List<Chapter> selectByAlbumId(String albumId) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        List<Chapter> select = chapterDao.select(chapter);
        return select;
    }
}
