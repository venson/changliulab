package com.venson.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;

public class EasyExcelTest {
    @Test
    public void readTest(){
        String file = "/Users/huangwenxuan/111.xlsx";
        EasyExcel.read(file, Student.class,new ExcelListener()).sheet().doRead();
    }
}
