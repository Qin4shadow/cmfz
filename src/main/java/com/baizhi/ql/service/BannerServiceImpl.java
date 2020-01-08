package com.baizhi.ql.service;

import com.baizhi.ql.dao.BannerDao;
import com.baizhi.ql.entity.Banner;
import com.baizhi.ql.entity.BannerPageDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> selectAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    //分页查
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public BannerPageDto selectPage(Integer curPage, Integer pageSize){
        BannerPageDto bannerPageDto = new BannerPageDto();
        // 设置当前页
        bannerPageDto.setPage(curPage);
        // 设置总行数
        int totalCount = bannerDao.selectCount(new Banner());
        bannerPageDto.setRecords(totalCount);
        // 设置总页
        bannerPageDto.setTotal(totalCount%pageSize==0? totalCount/pageSize : totalCount/pageSize+1);
        // 设置当前页的数据行
        List<Banner> banners = bannerDao.selectByRowBounds(null, new RowBounds((curPage - 1) * pageSize, pageSize));
        bannerPageDto.setRows(banners);
        return bannerPageDto;
    }

    @Override
    public Map insert(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        bannerDao.insert(banner);
        HashMap hashMap = new HashMap();
        hashMap.put("bannerId",banner.getId());
        return hashMap;
    }

    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(List ids) {
        bannerDao.deleteByIdList(ids);
    }

    @Override
    public void insertList(List list) {
        bannerDao.insertList(list);
    }

    @Override
    public List<Banner> queryBannersByTime() {
        List<Banner> banners = bannerDao.queryBannersByTime();
        return banners;
    }
}
