package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 随机密码工具
 */
@Slf4j
public class PasswordUtil {

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 20;

//    public static final String SPECIAL_CHARACTERS = "~!@#$%^&*()_";
    public static final String SPECIAL_CHARACTERS = "~!@#$%^&*()_+-=";

    /**
     * 获取符合强度要求的随机密码，长度不固定
     *    密码规则是：
     *    1、长度8~20（含）
     *    2、必须包含字母（大写或者小写）
     *    3、必须包含数字
     *    4、必须包含特殊字符，特殊字符有：~!@#$%^&*()_+-=
     * @param maxLength 最大密码长度，如果小于8则按8位生成，如果大于20则按20位生成
     * @return
     */
    public static String generate(int maxLength){
        if(maxLength < MIN_LENGTH){
            maxLength = MIN_LENGTH;
        }
        if(maxLength > MAX_LENGTH){
            maxLength = MAX_LENGTH;
        }
        // 1个特殊字符
        String special = randomSpecial(1);
        // 1个数字
        String num = StringUtil.randomNumeric(1);
        // 1个字母
        String alp = StringUtil.randomAlphabetic(1);
        // 其他字母+数字
        String other = StringUtil.randomAlphaNumeric(MIN_LENGTH-3);
        // 随机长度字母+数字
        String ran = "";
        if(maxLength > MIN_LENGTH){
//            Random sr = new Random(); // 此处使用Random以提升执行效率（SecureRandom）
            Random sr = new SecureRandom();
            int len = sr.nextInt(maxLength-MIN_LENGTH+1);
            if(len > 0){
                ran = StringUtil.randomAlphabetic(len);
            }
        }
        String result = StringUtil.shuffle(special+num+alp+other+ran);
        return result;
    }

    /**
     * 验证密码是否符合要求
     *    密码规则是：
     *    1、长度8~20（含）
     *    2、必须包含字母（大写或者小写）
     *    3、必须包含数字
     *    4、必须包含特殊字符，特殊字符有：~!@#$%^&*()_+-=
     * @param pwd
     * @return
     */
    public static boolean check(String pwd){
        String sc = new String("\\") + Arrays.stream(SPECIAL_CHARACTERS.split("")).collect(Collectors.joining("\\"));
        // String regex = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=])^.{8,20}$";
        String regex = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*["+sc+"])^.{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 产生一个长度为指定的随机特殊字符的字符数
     * @param count
     * @return
     */
    private static String randomSpecial(int count){
        return StringUtil.random(count, SPECIAL_CHARACTERS);
    }

}
