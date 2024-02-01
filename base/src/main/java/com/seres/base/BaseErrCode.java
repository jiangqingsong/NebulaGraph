package com.seres.base;

/**
 错误码定义规范，xxyyzzz
 http错误：xxx
 通用错误：10yyxxx
 网关错误：15yyxxx
 公共服务：20yyxxx
 xxx错误：25yyxxx
 yyy错误：30yyxxx
 */
public class BaseErrCode {

    // http error code
    public static final Error UNAUTHORIZED= new Error(401, "未登录或登录失效");
    public static final Error FORBIDDEN = new Error(403, "无操作权限");
    public static final Error NO_FOUND = new Error(404, "请求的资源不存在");
    public static final Error FORWARDING_ERROR = new Error(503, "后台服务不可用");


    // 10通用错误：10yyxxx
    // 1010通用系统错误：1010xxx
    public static final Error UNKNOWN_ERR = new Error(1010000, "未知错误");
    public static final Error ENCODE_ERR = new Error(1010001, "编码/加密/序列化失败");
    public static final Error DECODE_ERR = new Error(1010002, "解码/解密/反序列化失败");
    public static final Error KEY_GENERATE_ERR = new Error(1010003, "秘钥生成失败");
    public static final Error FILE_OPT_ERR = new Error(1010004, "文件操作失败");
    public static final Error FILE_UPLOAD_ERR = new Error(1010005, "文件上传失败");
    public static final Error FILE_DOWNLOAD_ERR = new Error(1010006, "文件下载失败");
    public static final Error NOT_FILE = new Error(1010007, "不是文件或文件不存在");
    public static final Error STREAM_OPT_ERR = new Error(1010008, "流操作失败");
    public static final Error COMPRESS_ERR = new Error(1010009, "压缩失败");
    public static final Error DECOMPRESS_ERR = new Error(1010010, "解压失败");

    // 1011通用请求错误：1011xxx
    public static final Error PARAM_ERR = new Error(1011001, "请求参数不合法");
    public static final Error BODY_ERR = new Error(1011002, "请求体内容不合法");
    public static final Error BODY_PARAM_ERR = new Error(1011003, "请求体参数不合法");
    public static final Error MAX_UPLOAD_SIZE_ERR = new Error(1011004, "文件过大");
    public static final Error AUTHORIZATION_CHECK_FAIL = new Error(1011005, "Authorization验证失败");
    public static final Error PARAM_VALIDATE_ERR = new Error(1011006, "参数校验失败");
    public static final Error IP_CHECK_FAIL = new Error(1011007, "IP验证失败");

}
