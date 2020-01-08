package com.baizhi.ql.controller;

import com.baizhi.ql.annotation.LogAnnotation;
import com.baizhi.ql.dao.AlbumDao;
import com.baizhi.ql.entity.Chapter;
import com.baizhi.ql.service.AlbumService;
import com.baizhi.ql.service.ChapterService;
import com.baizhi.ql.util.HttpUtil;
import jdk.nashorn.internal.codegen.SpillObjectCreator;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    //分页查
    @LogAnnotation(value = "章节查询")
    @RequestMapping("/selectPageChapter")
    public Map selectPageChapter(Integer page, Integer rows,String albumId){
        System.out.println(albumId);
        Map map = chapterService.selectPage(page, rows,albumId);
        return map;
    }

    //编辑判断是修改/删除/增加
    @RequestMapping("/saveChapter")
    public Map saveChapter(Chapter chapter, String oper, String[] id,String albumId){
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)){
            chapter.setAlbumId(albumId);
            chapterService.insert(chapter,albumId);
            hashMap.put("chapterId",chapter.getId());
        } else if ("edit".equals(oper)){
            chapter.setUrl(null);
            chapterService.update(chapter);
            hashMap.put("chapterId",chapter.getId());
        } else {
            chapterService.delete(Arrays.asList(id),albumId);
        }
        return hashMap;
    }
    //文件上传
    @RequestMapping("/uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpServletRequest request, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {

        String http = HttpUtil.getHttp(url, request, "/upload/voice/");
        //将文件存放到指定目录
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        // 计算音频时长
        String[] split = http.split("/");
        //获取文件名
        String name = split[split.length-1];
        // 通过文件获取AudioFile对象 音频解析对象
        String realPath = session.getServletContext().getRealPath("/upload/voice/");
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        chapterService.update(chapter);

        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }

    //文件下载
    @RequestMapping("/downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String name = split[split.length - 1];
        String realPath = session.getServletContext().getRealPath("/upload/voice/");
        File file = new File(realPath,name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(name,"utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }
}
