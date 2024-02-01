package com.seres.base.util;

import com.seres.base.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName WebExcelUtil，依赖easyexcel需要在使用的模块引入其jar
 * @description:
 * @author: liyu
 * @create: 2021-07-05 17:07
 **/
@Slf4j
public class WebExcelUtil {
    /**
     * web端生成excel文件的工具类方法
     * @param response response对象
     * @param fileName 文件名称
     * @param sheetName 工作表名称
     * @param dataList 数据列表
     * @param clazz data对应的实体对象，表头部分生成
     * @date 2021/7/5 17:15 
     * @return void
     */
    public static void write2Excel(HttpServletResponse response, String fileName, String sheetName, List<?> dataList, Class clazz){
        try {
            //设置编码
            fileName = new String(fileName.getBytes(), "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            //获取输出流
            OutputStream outputStream = response.getOutputStream();
            //调用commons里面生成excel工具类中的方法
            ExcelUtil.write2Excel(outputStream, sheetName, dataList, clazz);
        }catch (Exception e){
            log.error("生成excel文件失败，错误信息:{}", e.getMessage(), e);
            throw new AppException("生成excel文件失败");
        }
    }
}
