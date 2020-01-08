package com.baizhi.ql;

import com.baizhi.ql.dao.AlbumDao;
import com.baizhi.ql.dao.ChapterDao;
import com.baizhi.ql.entity.Album;
import com.baizhi.ql.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest
public class ChapterTest {
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    AlbumDao albumDao;
    @Test
    public void testSelectByPageAndAlbumId(){
        Chapter chapter = new Chapter();
        chapter.setAlbumId("2");
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds(0, 2));
        for (Chapter chapter1 : chapters) {
            System.out.println(chapter1);
        }
    }
    @Test
    public void testSelectAll(){
        Chapter chapter = new Chapter();
        chapter.setAlbumId("2");
        int i = chapterDao.selectCount(chapter);
        System.out.println(i);
    }
    @Test
    public void testOne(){
        Album album = albumDao.selectByPrimaryKey("92b63583-7187-43c6-9597-5a1b994499cf");
        System.out.println(album);
    }
}
