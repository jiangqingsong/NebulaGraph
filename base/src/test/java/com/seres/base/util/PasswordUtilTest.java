package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class PasswordUtilTest {

	@Test
	public void testGenerate(){
		log.info(PasswordUtil.generate(6));
		log.info(PasswordUtil.generate(30));
		log.info(PasswordUtil.generate(8));
		log.info(PasswordUtil.generate(12));
		log.info(PasswordUtil.generate(12));
	}

	@Test
	public void testCheck(){

		Assertions.assertFalse(PasswordUtil.check("1aaaaaa~111111111111111"));
		Assertions.assertFalse(PasswordUtil.check("1aaaa~"));
		Assertions.assertFalse(PasswordUtil.check("aaaaaaaaaa"));
		Assertions.assertFalse(PasswordUtil.check("1111111111"));
		Assertions.assertFalse(PasswordUtil.check("1aaaaaaa"));
		Assertions.assertFalse(PasswordUtil.check("1aaa aaa"));
		Assertions.assertFalse(PasswordUtil.check("1aaa|aaa"));

		Assertions.assertTrue(PasswordUtil.check("zaq1,lp-"));
		Assertions.assertTrue(PasswordUtil.check("=1aaaaaa"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa="));
		Assertions.assertTrue(PasswordUtil.check("aaa=1aaa"));
		Assertions.assertTrue(PasswordUtil.check("=11abaaa"));

		Assertions.assertTrue(PasswordUtil.check("=1aaaaaa}"));
		Assertions.assertTrue(PasswordUtil.check("=1aaaaaa\\"));
		Assertions.assertTrue(PasswordUtil.check("=1aaaaaa;"));
		Assertions.assertTrue(PasswordUtil.check("=1aaaaaa:"));
		Assertions.assertTrue(PasswordUtil.check("=1aaaaaa "));

		// ~!@#$%^&*()_+-=
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa~"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa!"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa@"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa#"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa$"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa%"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa^"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa&"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa*"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa("));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa)"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa_"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa+"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa-"));
		Assertions.assertTrue(PasswordUtil.check("1aaaaaa="));

	}
}
