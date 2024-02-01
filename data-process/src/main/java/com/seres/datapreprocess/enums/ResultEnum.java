package com.seres.datapreprocess.enums;

/**
 * @Description: 接口返回处理状态信息枚举类
 * @version v1.0
 * @author jiangqs
 * @date 2024/1/25 10:08
 */
public enum ResultEnum {
    _UNKNOWN_SERVER_ERROR(-2, "未知错误"),
    _SERVER_EXCEPTION(-3, "系统异常"),
    _SUCCESS(200, "执行成功"),
    _FAILED(-1, "执行失败"),
    _Baseline_NOT_NULL(1002, "基线模板中的基线不为空，无法删除"),
    _BAD_REQUEST(1001, "参数不正确"),
    _NAME_EXISTING(1003, "名称已经存在，不能添加"),
    _IP_EXISTING(1004, "IP已经存在，不能添加");


    private int enumCode;
    private String enumMsg;

    ResultEnum(int code, String msg){
        this.enumCode = code;
        this.enumMsg = msg;
    }

    public int getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(int enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumMsg() {
        return enumMsg;
    }

    public void setEnumMsg(String enumMsg) {
        this.enumMsg = enumMsg;
    }
}
