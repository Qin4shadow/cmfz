package com.baizhi.ql.entity;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    // 将Excel表格数据转换为 Java类型数据
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return cellData.getStringValue();
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String property = System.getProperty("user.dir");
        String[] split = value.split("/");
        value = split[split.length-1];
        String url = property+"/src/main/webapp/upload/img/"+value;
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }
}
