package com.seres.base.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author ：liyu
 * @date ：2022/9/8 15:56
 */
public class StringUtil extends StringUtils {

	private static char[] digits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+/".toCharArray();

	public static boolean isNotEmpty(@Nullable Object str){
		return !isEmpty(str);
	}

// 字符串相关三方工具类
//	org.apache.commons.lang.StringUtils
//	org.springframework.util.StringUtils
//  org.apache.commons.lang.RandomStringUtils

	/**
	 * 随机乱序，效率高于方法 Collections.shuffle(list) 近10倍
	 * @param original
	 * @return 乱序后的字符串
	 */
	public static String shuffle(String original){
		char[] arr = original.toCharArray();
		// Random sr = new Random(); // 此处使用Random以提升执行效率（SecureRandom）
		Random sr = new SecureRandom();
		char tmp;
		int j;
		for(int i=arr.length; i>1; i--){
			j = sr.nextInt(i);
			tmp = arr[i-1];
			arr[i-1] = arr[j];
			arr[j] = tmp;
		}
		return String.valueOf(arr);
	}

	/**
	 * 获取特定进制的字符串，参考JDK的Long.toString(long i, int radix)
	 * @param i 10进制数值
	 * @param radix 如果：radix < 2 || radix > 64 则按10进制输出
	 * @return
	 */
	public static String getRadixString(long i, int radix) {
		if (radix < 2 || radix > 64)
			radix = 10;
		if (radix == 10)
			return Long.toString(i);
		char[] buf = new char[65];
		int charPos = 64;
		boolean negative = (i < 0);
		if (!negative) {
			i = -i;
		}
		while (i <= -radix) {
			buf[charPos--] = digits[(int)(-(i % radix))];
			i = i / radix;
		}
		buf[charPos] = digits[(int)(-i)];

		if (negative) {
			buf[--charPos] = '-';
		}
		return new String(buf, charPos, (65 - charPos));
	}

	/**
	 * 使用指定的字符生成指定长度的随机字符串
	 * @param count 长度
	 * @param chars 可用字符数组
	 * @return
	 */
	public static String random(int count, char[] chars){
		return RandomStringUtils.random(count, chars);
	}

	/**
	 * 使用指定的字符生成指定长度的随机字符串
	 * @param count 长度
	 * @param chars 可用字符串
	 * @return
	 */
	public static String random(int count, String chars){
		return RandomStringUtils.random(count, chars);
	}

	/**
	 * 使用指定的字符生成指定长度范围的随机字符串
	 * @param minLengthInclusive 要生成的字符串的最小长度（包含）
	 * @param maxLengthExclusive 要生成的字符串的最大长度（包含）
	 * @param chars 字符数组
	 * @return
	 */
	public static String random(int minLengthInclusive , int maxLengthExclusive, char[] chars){
		maxLengthExclusive = maxLengthExclusive < minLengthInclusive ? minLengthInclusive : maxLengthExclusive;
		int randon = new SecureRandom().nextInt(maxLengthExclusive-minLengthInclusive+1) + minLengthInclusive;
		return random(randon, chars);
	}

	/**
	 * 使用指定的字符生成指定长度范围的随机字符串
	 * @param minLengthInclusive 要生成的字符串的最小长度（包含）
	 * @param maxLengthExclusive 要生成的字符串的最大长度（包含）
	 * @param chars 可用字符串
	 * @return
	 */
	public static String random(int minLengthInclusive , int maxLengthExclusive, String chars){
		maxLengthExclusive = maxLengthExclusive < minLengthInclusive ? minLengthInclusive : maxLengthExclusive;
		int randon = new SecureRandom().nextInt(maxLengthExclusive-minLengthInclusive+1) + minLengthInclusive;
		return random(randon, chars);
	}

	/**
	 * 生成指定长度的字母和数字随机组合字符串
	 * @param count 长度
	 * @return
	 */
	public static String randomAlphaNumeric(int count){
		return RandomStringUtils.randomAlphanumeric(count);
	}

	/**
	 * 生成指定长度范围的字母和数字随机组合字符串
	 * @param minLengthInclusive 要生成的字符串的最小长度（包含）
	 * @param maxLengthExclusive 要生成的字符串的最大长度（包含）
	 * @return
	 */
	public static String randomAlphaNumeric(int minLengthInclusive , int maxLengthExclusive){
		maxLengthExclusive = maxLengthExclusive < minLengthInclusive ? minLengthInclusive : maxLengthExclusive;
		int randon = new SecureRandom().nextInt(maxLengthExclusive-minLengthInclusive+1) + minLengthInclusive;
		return randomAlphaNumeric(randon);
	}

	/**
	 * 生成指定长度的字母随机组合字符串（包含大小写）
	 * @param count 长度
	 * @return
	 */
	public static String randomAlphabetic(int count){
		return RandomStringUtils.randomAlphabetic(count);
	}

	/**
	 * 生成指定长度范围的字母随机组合字符串（包含大小写）
	 * @param minLengthInclusive 要生成的字符串的最小长度（包含）
	 * @param maxLengthExclusive 要生成的字符串的最大长度（包含）
	 * @return
	 */
	public static String randomAlphabetic(int minLengthInclusive , int maxLengthExclusive){
		maxLengthExclusive = maxLengthExclusive < minLengthInclusive ? minLengthInclusive : maxLengthExclusive;
		int randon = new SecureRandom().nextInt(maxLengthExclusive-minLengthInclusive+1) + minLengthInclusive;
		return randomAlphabetic(randon);
	}

	/**
	 * 生成指定长度的数字随机组合字符串
	 * @param count 长度
	 * @return
	 */
	public static String randomNumeric(int count){
		return RandomStringUtils.randomNumeric(count);
	}

	/**
	 * 生成指定长度范围的数字随机组合字符串
	 * @param minLengthInclusive 要生成的字符串的最小长度（包含）
	 * @param maxLengthExclusive 要生成的字符串的最大长度（包含）
	 * @return
	 */
	public static String randomNumeric(int minLengthInclusive , int maxLengthExclusive){
		maxLengthExclusive = maxLengthExclusive < minLengthInclusive ? minLengthInclusive : maxLengthExclusive;
		int randon = new SecureRandom().nextInt(maxLengthExclusive-minLengthInclusive+1) + minLengthInclusive;
		return randomNumeric(randon);
	}

}
