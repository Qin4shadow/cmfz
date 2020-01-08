package com.baizhi.ql.dao;

import com.baizhi.ql.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface GuruDao extends SelectByIdListMapper<Guru,String>, Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
}
