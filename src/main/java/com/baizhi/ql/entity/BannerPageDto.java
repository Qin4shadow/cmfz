package com.baizhi.ql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerPageDto implements Serializable {
    //当前页
    private Integer page;
    //总页数
    private Integer total;
    //总行数
    private Integer records;
    //设置当前页的数据行
    private List<Banner> rows;
}
