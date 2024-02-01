package com.seres.base.util;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO操作工具类
 */
@Slf4j
public class IOUtil {

    /**
     * 将输入流写入输出流，缓冲大小8192
     * @param in 输入流，需自行处理流的关闭
     * @param out 输出流，需自行处理流的关闭
     * @return
     */
    public static void copy(InputStream in, OutputStream out){
        try {
            IOUtils.copy(in, out);
            out.flush();
        }catch (Exception e){
            log.error("输入流写入输出流失败", e);
            throw new AppException(BaseErrCode.STREAM_OPT_ERR);
        }
    }

    /**
     * 将输入流转为字节数组
     * @param in 输入流，需自行处理流的关闭
     * @return
     */
    public static byte[] toByteArray(InputStream in){
        try {
            return IOUtils.toByteArray(in);
        }catch (Exception e){
            log.error("将输入流转为字节数组失败", e);
            throw new AppException(BaseErrCode.STREAM_OPT_ERR);
        }
    }

}
