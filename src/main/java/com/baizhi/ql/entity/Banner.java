package com.baizhi.ql.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
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

@ContentRowHeight(40) //内容行高
@HeadRowHeight(20)    //头部行高
@ColumnWidth(30)      //列宽
public class Banner implements Serializable {

  @Id
  @ExcelProperty(value = {"Banner专辑","ID"})
  private String id;
  @ExcelProperty(value = {"Banner专辑","标题"})
  private String title;
  @ExcelProperty(value = {"Banner专辑","封面"},converter = ImageConverter.class)
  private String url;
  @ExcelProperty(value = {"Banner专辑","超链接"})
  private String href;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = {"Banner专辑","创建时间"})
  private Date createDate;
  @Column(name = "`desc`")
  @ExcelProperty(value = {"Banner专辑","描述"})
  private String desc;
  @Column(name = "`status`")
  @ExcelProperty(value = {"Banner专辑","状态"})
  private String status;

}
