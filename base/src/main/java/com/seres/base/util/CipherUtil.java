package com.seres.base.util;

import com.seres.base.BaseErrCode;
import com.seres.base.Constants;
import com.seres.base.exception.AppException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;

/**
 * 编解码/加解密工具
 * @author ：liyu
 * @date ：2022/9/6 18:29
 */
@Slf4j
public class CipherUtil {

	private static final String SRC_SIZE = "srcSize=";

	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS7Padding";
	private static final String AES_ECB = "AES/ECB/PKCS7Padding";
	private static final String MD5 = "MD5";
	private static final String SHA1 = "SHA-1";
	private static final String CHARSET_DEFAULT = Constants.UTF_8;
	// 16/24/32位秘钥。请注意：1.部分jdk不支持24/32位长度；2.前端页面在使用，请勿随意更改。
	public static final String AES_KEY_DEFAULT = "BRD_SRD_zaq1,lp-";
	public static final String AES_IV_DEFAULT = "BRD_SRD_zaq1,lp-"; // 16位偏移向量

	static {
		//因为jdk本身只支持pkcs5，加代码以及修改jdk配置支持pkcs7
		Security.addProvider(new BouncyCastleProvider());
	}

	private CipherUtil(){
	}

	/**
	 * 获取字符串的MD5哈希值
	 * @param src 原文
	 * @return 小写16进制MD5字符串
	 */
	public static String hashByMD5(@NonNull String src) {
		return hash(src.getBytes(), MD5,"src=" + src);
	}

	/**
	 * 获取文件的MD5哈希值
	 * @param src 文件
	 * @return 小写16进制MD5字符串
	 */
	public static String hashByMD5(@NonNull File src){
		return hash(FileUtil.getBytes(src), MD5, "fileName=" + src.getName());
	}

	/**
	 * 获取字节数组MD5哈希值
	 * @param src 字节数组
	 * @return 小写16进制MD5字符串
	 */
	public static String hashByMD5(@NonNull byte[] src){
		return hash(src, MD5, SRC_SIZE + src.length);
	}

	/**
	 * 获取字节数组MD5哈希值
	 * @param src 字节数组
	 * @return MD5字节数组
	 */
	public static byte[] hashByMD5ToByte(@NonNull byte[] src){
		return hashToByte(src, MD5, SRC_SIZE + src.length);
	}


	/**
	 * 获取字符串的SHA1哈希值
	 * @param src 原文
	 * @return 小写16进制SHA1字符串
	 */
	public static String hashBySHA1(@NonNull String src) {
		return hash(src.getBytes(), SHA1,"src=" + src);
	}

	/**
	 * 获取文件的SHA1哈希值
	 * @param src 文件
	 * @return 小写16进制SHA1字符串
	 */
	public static String hashBySHA1(@NonNull File src){
		return hash(FileUtil.getBytes(src), SHA1, "fileName=" + src.getName());
	}

	/**
	 * 获取字节数组SHA1哈希值
	 * @param src 字节数组
	 * @return 小写16进制SHA1字符串
	 */
	public static String hashBySHA1(@NonNull byte[] src){
		return hash(src, SHA1, SRC_SIZE + src.length);
	}

	/**
	 * 获取字节数组SHA1哈希值
	 * @param src 字节数组
	 * @return SHA1字节数组
	 */
	public static byte[] hashBySHA1ToByte(@NonNull byte[] src){
		return hashToByte(src, SHA1, SRC_SIZE + src.length);
	}


	/**
	 * 获取字节数组哈希值
	 * @param src 字节数组
	 * @param algorithm hash算法
	 * @param logMsg 错误日志信息
	 * @return 小写16进制hash字符串
	 */
	public static String hash(@NonNull byte[] src, @NonNull String algorithm, String logMsg){
		return byteToHexString(hashToByte(src, algorithm, logMsg));
	}

	/**
	 * 获取字节数组哈希值
	 * @param src 字节数组
	 * @param algorithm hash算法
	 * @param logMsg 错误日志信息
	 * @return hash字节数组
	 */
	public static byte[] hashToByte(@NonNull byte[] src, @NonNull String algorithm, String logMsg){
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] digest = md.digest(src);
			return digest;
		} catch (Exception e) {
			log.error("获取{}哈希值失败：{}，{}", algorithm, e.getMessage(), logMsg);
			throw new AppException(BaseErrCode.ENCODE_ERR);
		}
	}


	/**
	 * Url编码
	 * @param src 编码前的原文
	 * @return
	 */
	public static String encodeByUrl(@NonNull String src){
		try {
			return URLEncoder.encode(src, CHARSET_DEFAULT);
		} catch (Exception e) {
			log.error("Url编码失败：{}，src={}", e.getMessage(), src);
			throw new AppException(BaseErrCode.ENCODE_ERR);
		}
	}

	/**
	 * Url解码
	 * @param encoded 编码字符串
	 * @return
	 */
	public static String decodeByUrl(@NonNull String encoded){
		try {
			return URLDecoder.decode(encoded, CHARSET_DEFAULT);
		} catch (Exception e) {
			log.error("Url解码失败：{}，encoded={}", e.getMessage(), encoded);
			throw new AppException(BaseErrCode.DECODE_ERR);
		}
	}


	/**
	 * Base64编码
	 * @param src 编码前的字节数组原文
	 * @return
	 */
	public static String encodeByBase64(@NonNull byte[] src){
		try {
			return Base64.getEncoder().encodeToString(src);
		} catch (Exception e) {
			log.error("Base64编码失败：{}，src={}", e.getMessage(), src);
			throw new AppException(BaseErrCode.ENCODE_ERR);
		}
	}

	/**
	 * Base64编码
	 * @param src 编码前的原文
	 * @return
	 */
	public static String encodeByBase64(@NonNull String src){
		return encodeByBase64(src.getBytes());
	}

	/**
	 * Base64解码
	 * @param encoded 编码字符串
	 * @return
	 */
	public static byte[] decodeByBase64Byte(@NonNull String encoded){
		try {
			return Base64.getDecoder().decode(encoded);
		} catch (Exception e) {
			log.error("Base64解码失败：{}，encoded={}", e.getMessage(), encoded);
			throw new AppException(BaseErrCode.DECODE_ERR);
		}
	}

	/**
	 * Base64解码
	 * @param encoded 编码字符串
	 * @return
	 */
	public static String decodeByBase64(@NonNull String encoded){
		return new String(decodeByBase64Byte(encoded));
	}


	/**
	 * AES-CBC模式加密
	 * @param src 加密前的原文字符串
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @param iv 偏移向量，为空则使用默认值
	 * @return 加密后的base64结果
	 */
	public static String encryptByAES_CBCToBase64(@NonNull String src, String key, String iv) {
		return encryptByAES_CBCToBase64(src.getBytes(), key, iv);
	}

	/**
	 * AES-CBC模式加密
	 * @param src 加密前的原文字节数组
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @param iv 偏移向量，为空则使用默认值
	 * @return 加密后的base64结果
	 */
	public static String encryptByAES_CBCToBase64(@NonNull byte[] src, String key, String iv) {
		return encodeByBase64(encryptByAES_CBCToByte(src, key, iv));
	}

	/**
	 * AES-CBC模式加密
	 * @param src 加密前的原文字节数组
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @param iv 偏移向量，为空则使用默认值
	 * @return 加密后的字节数组
	 */
	public static byte[] encryptByAES_CBCToByte(@NonNull byte[] src, String key, String iv) {
		try {
			if(StringUtil.isEmpty(key)){
				key = AES_KEY_DEFAULT;
			}
			if(StringUtil.isEmpty(iv)){
				iv = AES_IV_DEFAULT;
			}
			// Cipher：密码，获取加密对象，参数表示使用什么类型加密
			Cipher cir = Cipher.getInstance(AES_CBC);
			// 指定秘钥规则；第一个参数表示：密钥，key的字节数组，长度必须是16位；第二个参数表示：算法
			SecretKeySpec sks = new SecretKeySpec(key.getBytes(), AES);
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			// 对加密进行初始化；第一个参数：表示模式，有加密模式和解密模式；第二个参数：表示秘钥规则；第三个参数：表示偏移向量
			cir.init(Cipher.ENCRYPT_MODE, sks, ips);
			// 进行加密
			byte[] bytes = cir.doFinal(src);
			return bytes;
		}catch (Exception e){
			log.error("AES加密失败：{}，src={}", e.getMessage(), src);
			throw new AppException(BaseErrCode.ENCODE_ERR);
		}
	}

	/**
	 * AES-CBC模式解密
	 * @param cipher 加密后的base64密文
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @param iv 偏移向量，为空则使用默认值
	 * @return 明文字符串
	 */
	public static String decryptByAES_CBCFromBase64(@NonNull String cipher, String key, String iv) {
		return new String(decryptByAES_CBCFromBase64ToByte(cipher, key, iv));
	}

	/**
	 * AES-CBC模式解密
	 * @param cipher 加密后的base64密文
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @param iv 偏移向量，为空则使用默认值
	 * @return 明文字节数组
	 */
	public static byte[] decryptByAES_CBCFromBase64ToByte(@NonNull String cipher, String key, String iv) {
		try {
			if(StringUtil.isEmpty(key)){
				key = AES_KEY_DEFAULT;
			}
			if(StringUtil.isEmpty(iv)){
				iv = AES_IV_DEFAULT;
			}
			// Cipher：密码，获取加密对象，参数表示使用什么类型加密
			Cipher cir = Cipher.getInstance(AES_CBC);
			// 指定秘钥规则；第一个参数表示：密钥，key的字节数组，长度必须是16位，第二个参数表示：算法
			SecretKeySpec sks = new SecretKeySpec(key.getBytes(), AES);
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			// 对加密进行初始化；第一个参数：表示模式，有加密模式和解密模式；第二个参数：表示秘钥规则；第三个参数：表示偏移向量
			cir.init(Cipher.DECRYPT_MODE,sks, ips);
			// 进行解密
			byte [] inputBytes = decodeByBase64Byte(cipher);
			byte[] bytes = cir.doFinal(inputBytes);
			return bytes;
		}catch (Exception e){
			log.error("AES解密失败：{}，input={}", e.getMessage(), cipher);
			throw new AppException(BaseErrCode.DECODE_ERR);
		}
	}


	/**
	 * AES-ECB模式加密，返回16进制密文
	 * @param src 加密前的原文
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @return 加密后的16进制结果
	 */
	public static String encryptByAES_ECBToHexString(@NonNull String src, String key) {
		return byteToHexString(encryptByAES_ECBToByte(src, key));
	}

	/**
	 * AES-ECB模式加密
	 * @param src 加密前的原文
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @return 加密后的字节数组
	 */
	public static byte[] encryptByAES_ECBToByte(@NonNull String src, String key) {
		try {
			if(StringUtil.isEmpty(key)){
				key = AES_KEY_DEFAULT;
			}
			// Cipher：密码，获取加密对象，参数表示使用什么类型加密
			Cipher cir = Cipher.getInstance(AES_ECB);
			// 指定秘钥规则；第一个参数表示：密钥，key的字节数组，长度必须是16位；第二个参数表示：算法
			SecretKeySpec sks = new SecretKeySpec(key.getBytes(), AES);
			// 对加密进行初始化；第一个参数：表示模式，有加密模式和解密模式；第二个参数：表示秘钥规则
			cir.init(Cipher.ENCRYPT_MODE, sks);
			// 进行加密
			byte[] bytes = cir.doFinal(src.getBytes());
			return bytes;
		}catch (Exception e){
			log.error("AES加密失败：{}，src={}", e.getMessage(), src);
			throw new AppException(BaseErrCode.ENCODE_ERR);
		}
	}

	/**
	 * AES-ECB模式解密，解密16制作密文
	 * @param cipher 加密后的16进制密文
	 * @param key 16/24/32位秘钥，为空则使用默认值
	 * @return 明文
	 */
	public static String decryptByAES_ECBFromHexString(@NonNull String cipher, String key) {
		try {
			if(StringUtil.isEmpty(key)){
				key = AES_KEY_DEFAULT;
			}
			// Cipher：密码，获取加密对象，参数表示使用什么类型加密
			Cipher cir = Cipher.getInstance(AES_ECB);
			// 指定秘钥规则；第一个参数表示：密钥，key的字节数组，长度必须是16位，第二个参数表示：算法
			SecretKeySpec sks = new SecretKeySpec(key.getBytes(), AES);
			// 对加密进行初始化；第一个参数：表示模式，有加密模式和解密模式；第二个参数：表示秘钥规则
			cir.init(Cipher.DECRYPT_MODE,sks);
			// 进行解密
			byte [] inputBytes = hexStringToByte(cipher);
			byte[] bytes = cir.doFinal(inputBytes);
			return new String(bytes);
		}catch (Exception e){
			log.error("AES解密失败：{}，input={}", e.getMessage(), cipher);
			throw new AppException(BaseErrCode.DECODE_ERR);
		}
	}


	/**
	 * byte数组转16进制字符串
	 * @param sources
	 * @return 小写16进制值
	 */
	public static String byteToHexString(byte[] sources) {
		if (sources == null || sources.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (byte source : sources) {
			String result = Integer.toHexString(source& 0xff);
			if (result.length() < 2) {
				result = "0" + result;
			}
			sb.append(result);
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转byte数组
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToByte(String hexString) {
		if (hexString.length() % 2 != 0) {
			throw new IllegalArgumentException("hexString length not valid");
		}
		int length = hexString.length() / 2;
		byte[] resultBytes = new byte[length];
		for (int index = 0; index < length; index++) {
			String result = hexString.substring(index * 2, index * 2 + 2);
			resultBytes[index] = Integer.valueOf(Integer.parseInt(result, 16)).byteValue();
		}
		return resultBytes;
	}

}
