package com.baizhi.ql.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {

  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String broadcast;
  @Column(name = "`count`")
  private Integer count;
  @Column(name = "`desc`")
  private String desc;
  @Column(name = "`status`")
  private String status;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createDate;
  private String picture;

}
