package com.baizhi.ql.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.ql.annotation.LogAnnotation;
import com.baizhi.ql.entity.Banner;
import com.baizhi.ql.entity.BannerDataListener;
import com.baizhi.ql.entity.BannerPageDto;
import com.baizhi.ql.service.BannerService;
import com.baizhi.ql.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    //查所有
    @ResponseBody
    @RequestMapping("/selectAll")
    public List<Banner> selectAll(){
        List<Banner> banners = bannerService.selectAll();
        return banners;
    }
    //分页查
    @LogAnnotation(value = "轮播图查询")
    @ResponseBody
    @RequestMapping("/selectPageBanner")
    public BannerPageDto selectPageBanner(Integer page, Integer rows){
        BannerPageDto bannerPageDto = bannerService.selectPage(page, rows);
        return bannerPageDto;
    }

    //编辑判断是修改/删除/增加
    @RequestMapping("saveBanner")
    @ResponseBody
    public Map saveBanner(Banner banner,String oper,String[] id){
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)){
            bannerService.insert(banner);
            hashMap.put("bannerId",banner.getId());
        } else if ("edit".equals(oper)){
            banner.setUrl(null);
            bannerService.update(banner);
            hashMap.put("bannerId",banner.getId());
        } else {
            bannerService.delete(Arrays.asList(id));
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("/uploadBanner")
    @ResponseBody
    public Map uploadBanner(MultipartFile url, String bannerId,HttpServletRequest request){

        String http = HttpUtil.getHttp(url, request, "/upload/img/");
        //将文件存放到指定目录
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(http);
        bannerService.update(banner);

        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }


    //EasyExcel导出轮播图信息
    @RequestMapping("/outBannerInformation")
    @ResponseBody
    public Map outBannerInformation(){
        List<Banner> banners = bannerService.selectAll();
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String url = split[split.length-1];
            banner.setUrl(url);
        }
        String fileName = "F:\\资料\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xls";
        EasyExcel.write(fileName,Banner.class).sheet("banner").doWrite(banners);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    //Excel模板下载
    @RequestMapping("/outBannerModel")
    @ResponseBody
    public Map outBannerModel(){
        Banner banner = new Banner();
        String fileName = "F:\\资料\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xls";
        EasyExcel.write(fileName,Banner.class).sheet("banner").doWrite(Arrays.asList(banner));
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    //EasyExcel导入轮播图信息
    @RequestMapping("/inBannerInformation")
    @ResponseBody
    public Map inBannerInformation(MultipartFile inputBanner,HttpServletRequest request){
        String http = HttpUtil.getHttp(inputBanner, request, "/upload/inputBanner/");
        String realPath = request.getSession().getServletContext().getRealPath("/upload/inputBanner/");
        String[] split = http.split("/");
        String name = realPath + split[split.length-1];
        EasyExcel.read(name,Banner.class,new BannerDataListener()).sheet().doRead();
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }








//    //Poi导出轮播图信息
//    @RequestMapping("/outBannerInformation")
//    @ResponseBody
//    public Map outBannerInformation(){
//        List<Banner> banners = bannerService.selectAll();
//        //创建excel表格对象
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        //工作簿对象
//        HSSFSheet sheet = workbook.createSheet();
//        //行对象
//        HSSFRow row = sheet.createRow(0);
//        //表格的头部分
//        String[] str = {"ID","标题","图片","超链接","创建时间","描述","状态"};
//        for (int i = 0; i < str.length; i++) {
//            String s = str[i];
//            //单元格对象
//            row.createCell(i).setCellValue(s);
//        }
//        // 通过workbook对象获取样式对象
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        // 通过workbook对象获取数据格式化处理对象
//        HSSFDataFormat dataFormat = workbook.createDataFormat();
//        // 指定格式化样式 如 yyyy-MM-dd
//        short format = dataFormat.getFormat("yyyy-MM-dd");
//        // 为样式对象 设置格式化处理
//        cellStyle.setDataFormat(format);
//        for (int i = 0; i < banners.size(); i++) {
//            Banner banner = banners.get(i);
//            HSSFRow row1 = sheet.createRow(i + 1);
//            row1.createCell(0).setCellValue(banner.getId());
//            row1.createCell(1).setCellValue(banner.getTitle());
//            row1.createCell(2).setCellValue(banner.getUrl());
//            row1.createCell(3).setCellValue(banner.getHref());
//            HSSFCell cell = row1.createCell(4);
//            cell.setCellStyle(cellStyle);
//            cell.setCellValue(banner.getCreateDate());
//            row1.createCell(5).setCellValue(banner.getDesc());
//            row1.createCell(6).setCellValue(banner.getStatus());
//        }
//        try {
//            workbook.write(new File("F:\\资料\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xls"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HashMap hashMap = new HashMap();
//        hashMap.put("status",200);
//        return hashMap;
//    }
//
//    //Poi导入轮播图信息
//    @RequestMapping("/inBannerInformation")
//    @ResponseBody
//    public Map inBannerInformation(MultipartFile inputBanner,HttpServletRequest request) throws IOException {
//        String http = HttpUtil.getHttp(inputBanner, request, "/upload/inputBanner/");
//        // 原始数据
//        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(http));
//        // 数据的封装
//        HSSFSheet sheet = workbook.getSheet("Sheet0");
//        ArrayList<Banner> banners = new ArrayList<>();
//        // sheet.getLastRowNum() 获取最后一行的行号 注意使用<= 遍历
//        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//            Banner banner = new Banner();
//            HSSFRow row = sheet.getRow(i);
//            String cell1 = row.getCell(0).getStringCellValue();
//            String stringCellValue = row.getCell(6).getStringCellValue();
//            double numericCellValue = row.getCell(7).getNumericCellValue();
//            System.out.println(numericCellValue);
//            banner.setId(cell1);
//            banner.setStatus(stringCellValue);
//            banners.add(banner);
//        }
//        HashMap hashMap = new HashMap();
//        hashMap.put("status",200);
//        return hashMap;
//    }

}
