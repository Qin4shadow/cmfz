package com.baizhi.ql.dao;

import com.baizhi.ql.cache.UserCache;
import com.baizhi.ql.entity.MapUserDto;
import com.baizhi.ql.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


//@CacheNamespace(implementation = UserCache.class)
public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    List<MapUserDto> queryLocationBySex(String sex);
    List<User> selectFive();
}
