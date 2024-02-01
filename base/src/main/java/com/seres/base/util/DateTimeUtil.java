package com.seres.base.util;

import com.seres.base.Constants;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateTimeUtil extends DateUtil {

    /**
     * 字符串转日期，支持多种字符串日期格式，同parse(dateStr)方法
     * @return date
     */
    public static Date toDate(String dateStr){
        return parse(dateStr);
    }

    /**
     * 日期转换字符串 yyyy-MM-dd HH:mm:ss 格式，同format(date)方法
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toString(Date date){
        return format(date);
    }

    /**
     * 格式化为 yyyy-MM-dd HH:mm:ss 格式，同formatDateTime(date)方法
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date){
        return formatDateTime(date);
    }

    /**
     * 格式化为指定格式时间字符串
     * @return
     */
    public static String format(long date){
        return format(new Date(date));
    }

    /**
     * 格式化为指定格式时间字符串
     * @return
     */
    public static String format(long date, String format){
        return format(new Date(date), format);
    }

    /**
     * 格式化为 yyyyMMddHHmmss 格式
     * @return yyyyMMddHHmmss
     */
    public static String formatDateTime14(Date date){
        return format(date, Constants.DF_YMDHMS14);
    }

    /**
     * 获取当前时间，格式：yyyy-MM-dd HH:mm:ss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String currentDateTime(){
        return currentDateTime(Constants.DF_YMDHMS);
    }

    /**
     * 获取当前时间，格式：yyyyMMddHHmmss
     * @return yyyyMMddHHmmss
     */
    public static String currentDateTime14(){
        return currentDateTime(Constants.DF_YMDHMS14);
    }

    /**
     * 获取当前时间，格式：yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String currentDate(){
        return currentDateTime(Constants.DF_YMD);
    }

    /**
     * 获取当前时间
     * @param format
     * @return format格式时间
     */
    public static String currentDateTime(String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(LocalDateTime.now());
    }

    //========= 时间加减（前后），详见

    /**
     * 获取当前月1号
     * @return yyyy-MM-01
     */
    public static String currentMonthFirstDay(){
        return getBeforeMonthFirstDay(0);
    }

    /**
     * 获取前几月1号
     * @param month
     * @return yyyy-MM-01
     */
    public static String getBeforeMonthFirstDay(int month){
        Calendar c = Calendar.getInstance();
        //过去第month-1个月（当月算第一个月），第1天
        c.add(Calendar.MONTH, -month);
        c.set(Calendar.DATE, 1);
        return formatDate(c.getTime());
    }

    /**
     * 获取后几月1号
     * @param month
     * @return yyyy-MM-01
     */
    public static String getAfterMonthFirstDay(int month){
        Calendar c = Calendar.getInstance();
        //过去第month-1个月（当月算第一个月），第1天
        c.add(Calendar.MONTH, month);
        c.set(Calendar.DATE, 1);
        return formatDate(c.getTime());
    }

    /**
     * 获取前几天日期
     * @param days
     * @return yyyy-MM-dd
     */
    public static String getBeforeDay(int days){
        return offsetDay(new Date(), -days).toString(Constants.DF_YMD);
    }

    /**
     * 获取后几天日期
     * @param days
     * @return yyyy-MM-dd
     */
    public static String getAfterDay(int days){
        return offsetDay(new Date(), days).toString(Constants.DF_YMD);
    }

    /**
     * 获取前几分钟
     * @param minutes 正数，如：10、60、120
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getBeforeMinute(int minutes){
        return offsetMinute(new Date(), -minutes).toString(Constants.DF_YMDHMS);
    }

    /**
     * 获取后几分钟
     * @param minutes 正数，如：10、60、120
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getAfterMinute(int minutes){
        return offsetMinute(new Date(), minutes).toString(Constants.DF_YMDHMS);
    }

    /**
     * 获取当前系统毫秒时间戳
     * @return 10进制，2286-07-01前保证只有13位
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前16进制毫秒时间戳
     * @return 16进制，2286-07-01前保证只有11位
     */
    public static String current16HexTimeMillis(){
        return Long.toHexString(currentTimeMillis());
    }

    /**
     * 获取当前32进制毫秒时间戳
     * @return 32进制，2286-07-01前保证只有9位
     */
    public static String current32HexTimeMillis(){
        return Long.toUnsignedString(currentTimeMillis(), 32);
    }

    /**
     * 获取当前62进制毫秒时间戳
     * @return 62进制，2081-08-05 18:16:46 207及其之前的时间保证只有7位
     */
    public static String current62HexTimeMillis(){
        return StringUtil.getRadixString(currentTimeMillis(), 62);
    }

    /**
     * 获取当前系统时间的微秒部分
     * @return 3位10进制
     */
    private static long currentMicrosPart() {
        long nt = System.nanoTime() / 1000;
        long micros = (nt - nt / 1000 * 1000);
        return micros;
    }

    /**
     * 获取当前系统微秒时间戳
     * @return 10进制，2286-07-01前保证只有16位
     */
    public static long currentTimeMicros() {
        long millis = currentTimeMillis();
        long mp = currentMicrosPart();
        return millis * 1000 + mp;
    }

    /**
     * 获取当前16进制微秒时间戳
     * @return 16进制，2112-07-01前保证只有13位
     */
    public static String current16HexTimeMicros(){
        return Long.toHexString(currentTimeMicros());
    }

    /**
     * 获取当前32进制微秒时间戳
     * @return 32进制，2286-07-01前保证只有11位
     */
    public static String current32HexTimeMicros(){
        return Long.toUnsignedString(currentTimeMicros(), 32);
    }

    /**
     * 获取当前62进制微秒时间戳
     * @return 62进制，2286-07-01前保证只有9位
     */
    public static String current62HexTimeMicros(){
        return StringUtil.getRadixString(currentTimeMicros(), 62);
    }

}
