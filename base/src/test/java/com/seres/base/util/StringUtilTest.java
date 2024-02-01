package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class StringUtilTest {

	@Test
	public void test(){
		log.info(StringUtil.randomAlphaNumeric(2));
		log.info(StringUtil.randomAlphaNumeric(2, 4));
		log.info(StringUtil.randomAlphaNumeric(2, 4));
		log.info(StringUtil.randomAlphaNumeric(4, 4));
	}

}
