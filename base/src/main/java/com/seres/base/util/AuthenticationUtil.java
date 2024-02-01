package com.seres.base.util;

import com.seres.base.Constants;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 认证（签名、验签）
 * @author ：liyu
 * @date ：2022/9/7 18:12
 */
@Slf4j
public class AuthenticationUtil {

	private static final String TOKEN_DEFAULT = "BRD_SRD_zaq1,lp-";

	/**
	 * 签名
	 * token令牌+时间戳（yyyyMMddHHmmss）进行MD5（此处token令牌+时间戳中间没有分隔符，MD5值用大写，
	 * 如：ABBCCCDDDDEEEEE20210829112320），在此基础上再将MD5值+时间戳（填写方式为MD5:时间戳）进行BASE64编码
	 * @param token 签名token
	 * @return 签名结果
	 */
	public static String signByRule1(String token){
		if(StringUtil.isEmpty(token)){
			token = TOKEN_DEFAULT;
		}
		String date = DateTimeUtil.currentDateTime14();
		// MD5(token+date)
		String md5 = CipherUtil.hashByMD5(token + date).toUpperCase();
		// Base64(MD5+:+date)
		String result = CipherUtil.encodeByBase64(md5 + ":" + date);
		return result;
	}

	/**
	 * 签名，使用默认token
	 * token令牌+时间戳（yyyyMMddHHmmss）进行MD5（此处token令牌+时间戳中间没有分隔符，MD5值用大写，
	 * 如：ABBCCCDDDDEEEEE20210829112320），在此基础上再将MD5值+时间戳（填写方式为MD5:时间戳）进行BASE64编码
	 * @return 签名结果
	 */
	public static String signByRule1(){
		return signByRule1(null);
	}

	/**
	 * 验证签名
	 * @param signature 规则1的签名值，参考：signByRule1方法
	 * @param token 签名token
	 * @param intervalSeconds 有效（正负）时间间隔，单位秒，小于等于0则不校验时间间隔
	 * @return 是否通过
	 */
	public static boolean checkByRule1(String signature, String token, long intervalSeconds){
		if(StringUtil.isEmpty(token)){
			token = TOKEN_DEFAULT;
		}
		if(StringUtil.isEmpty(signature)){
			log.error("认证失败，签名字段为空");
			return false;
		}
		// BASE64解码获取MD5和时间戳
		String s1 = CipherUtil.decodeByBase64(signature);
		String[] split = s1.split(":");
		if(split.length != 2){
			log.error("认证失败，base64后格式错误，base64解码值={}，原始签名值={}", s1, signature);
			return false;
		}
		String md5 = split[0];
		String date = split[1];
		// 验证时间间隔
		if(intervalSeconds > 0){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DF_YMDHMS14);
			try {
				long time = simpleDateFormat.parse(date).getTime(); //获取传入时间戳
				long now = System.currentTimeMillis();
				long interval = intervalSeconds * 1000;
				if (time - now > interval || now - time > interval){
					log.error("认证失败，超过有效时间间隔，请求中的时间={}，原始签名值={}", date, signature);
					return false;
				}
			} catch (ParseException e) {
				log.error("认证失败，时间格式错误，请求中的时间={}，原始签名值={}", date, signature);
				return false;
			}
		}
		// 验证MD5值，MD5(token+date)
		String myMd5 = CipherUtil.hashByMD5(token + date).toUpperCase();
		if(!md5.equals(myMd5)){
			log.error("认证失败，MD5值不匹配，请求中的时间={}，请求中的MD5={}，系统计算的MD5={}，原始签名值={}", date, md5, myMd5, signature);
			return false;
		}
		return true;
	}

	/**
	 * 验证签名
	 * @param signature 规则1的签名值，参考：signByRule1方法
	 * @param intervalSeconds 有效（正负）时间间隔，单位秒，小于等于0则不校验时间间隔
	 * @return 是否通过
	 */
	public static boolean checkByRule1(String signature, long intervalSeconds){
		return checkByRule1(signature, null, intervalSeconds);
	}



}
