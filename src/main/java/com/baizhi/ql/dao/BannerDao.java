package com.baizhi.ql.dao;

import com.baizhi.ql.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends InsertListMapper<Banner>, Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
    //根据时间降序查五个
    List<Banner> queryBannersByTime();
}
