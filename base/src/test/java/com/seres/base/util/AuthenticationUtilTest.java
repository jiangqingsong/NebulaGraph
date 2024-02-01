package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 11:34
 */
@Slf4j
public class AuthenticationUtilTest {

	@Test
	public void testSignByRule1() {
		log.info(AuthenticationUtil.signByRule1());
	}

	@Test
	public void testCheckByRule1() {
		Assertions.assertTrue(AuthenticationUtil.checkByRule1("MDNFNEVBRTVEQzQ5NENBNzA2MDBGNkQyNjAxRjAyQzQ6MjAyMjA5MDgwOTM2MDM=", 0));
		Assertions.assertFalse(AuthenticationUtil.checkByRule1("MDNFNEVBRTVEQzQ5NENBNzA2MDBGNkQyNjAxRjAyQzQ6MjAyMjA5MDgwOTM2MDM=", 1800));
	}

}
