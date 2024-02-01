package com.seres.base.util;

import com.seres.base.exception.AppException;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @ClassName ExcelUtil，依赖easyexcel需要在使用的模块引入其jar
 * @description:
 * @author: liyu
 * @create: 2021-07-05 15:15
 **/
@Slf4j
public class ExcelUtil {

    /**
     * 生成excel
     * @param outputStream 输出流
     * @param sheetName 工作表名称
     * @param dataList 数据列表
     * @param clazz data对应的实体对象，表头部分生成
     * @date 2021/7/5 17:16 
     * @return void
     */
    public static void write2Excel(OutputStream outputStream, String sheetName, List<?> dataList, Class clazz){
        try {
            EasyExcel.write(outputStream,clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet(sheetName).doWrite(dataList);
        }catch (Exception e){
            log.error("生成excel文件失败，错误信息:{}", e.getMessage(), e);
            throw new AppException("生成excel文件失败");
        }
    }

    /**
     * 生成excel
     * @param outPath 输出文件位置
     * @param sheetName 工作表名称
     * @param dataList 数据列表
     * @param clazz data对应的实体对象，表头部分生成
     * @date 2021/7/5 17:16
     * @return void
     */
    public static void write2Excel(String outPath, String sheetName, List<?> dataList, Class clazz){
        //生成新的word
        File file = new File(outPath);
        try (FileOutputStream stream = new FileOutputStream(file)){
            write2Excel(stream, sheetName, dataList, clazz);
        }catch (Exception e){
            log.error("生成excel文件失败，错误信息:{}", e.getMessage(), e);
            throw new AppException("生成excel文件失败");
        }
    }
}
