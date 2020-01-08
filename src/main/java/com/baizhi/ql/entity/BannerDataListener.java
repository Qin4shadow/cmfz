package com.baizhi.ql.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.ql.dao.BannerDao;
import com.baizhi.ql.util.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

public class BannerDataListener extends AnalysisEventListener<Banner> {
    List<Banner> list = new ArrayList();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        list.add(banner);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        Object bean = ApplicationUtils.getBean(BannerDao.class);
        BannerDao bannerDao = (BannerDao)bean;
        bannerDao.insertList(list);
    }
}
