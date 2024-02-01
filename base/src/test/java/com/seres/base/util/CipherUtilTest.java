package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 11:34
 */
@Slf4j
public class CipherUtilTest {

	@Test
	public void test() {

		Assertions.assertEquals("1ef04e71a1e2f0f5e0ee6014296aca76", CipherUtil.encryptByAES_ECBToHexString("admin", CipherUtil.AES_KEY_DEFAULT));
		Assertions.assertEquals("6cdecc257a6d5bd75d8c7d25092785f9", CipherUtil.encryptByAES_ECBToHexString("zsdcfhrj#212", null));

		Assertions.assertEquals("admin", CipherUtil.decryptByAES_ECBFromHexString("1ef04e71a1e2f0f5e0ee6014296aca76", CipherUtil.AES_KEY_DEFAULT));
		Assertions.assertEquals("zsdcfhrj#212", CipherUtil.decryptByAES_ECBFromHexString("6cdecc257a6d5bd75d8c7d25092785f9", null));

		Assertions.assertEquals("csMX+u6ZYArtTeHfIVXgxg==", CipherUtil.encryptByAES_CBCToBase64("zsdcfhrj#212", CipherUtil.AES_KEY_DEFAULT, CipherUtil.AES_IV_DEFAULT));
		Assertions.assertEquals("zsdcfhrj#212", CipherUtil.decryptByAES_CBCFromBase64("csMX+u6ZYArtTeHfIVXgxg==", CipherUtil.AES_KEY_DEFAULT, CipherUtil.AES_IV_DEFAULT));

		String text = "加密原理：轮密钥加本身不难被破解，另外三个阶段分别提供了混淆和非线性功能。可是字节替换、行移位、列混淆阶段没有涉及密钥，就它们自身而言，并没有提供算法的安全性。但该算法经历一个分组的异或加密（轮密钥加），再对该分组混淆扩散（其他三个阶段），再接着又是异或加密，如此交替进行，这种方式非常有效非常安全。";
		String longText = text + text + text + text + text + text + text + text + text + text;
		log.info(CipherUtil.encryptByAES_CBCToBase64(longText, CipherUtil.AES_KEY_DEFAULT, CipherUtil.AES_IV_DEFAULT));

		log.info(CipherUtil.encodeByUrl("admin&?")); // admin%26%3F
		Assertions.assertEquals("admin%26%3F", CipherUtil.encodeByUrl("admin&?"));
		log.info(CipherUtil.decodeByUrl("admin%26%3F")); // admin&?
		Assertions.assertEquals("admin&?", CipherUtil.decodeByUrl("admin%26%3F"));

		log.info(CipherUtil.hashByMD5("admin")); // 21232f297a57a5a743894a0e4a801fc3
		Assertions.assertEquals("21232f297a57a5a743894a0e4a801fc3", CipherUtil.hashByMD5("admin"));

		log.info(CipherUtil.hashBySHA1("123456")); // 7c4a8d09ca3762af61e59520943dc26494f8941b
		Assertions.assertEquals("7c4a8d09ca3762af61e59520943dc26494f8941b", CipherUtil.hashBySHA1("123456"));
	}


}
