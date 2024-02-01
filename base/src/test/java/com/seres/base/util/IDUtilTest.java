package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class IDUtilTest {

	@Test
	public void test(){
		log.info(IDUtil.generatorUUIDShort());
		log.info(IDUtil.generatorUUID());
		log.info(IDUtil.generatorUUIDUpper());
		log.info(IDUtil.generatorUUID36());
		log.info(IDUtil.generatorUUIDUpper36());
		log.info("{}", IDUtil.generatorSnowflakeID());
		log.info("{}", IDUtil.generatorSnowflakeID());
		log.info("{}", IDUtil.generatorSnowflakeIDStr());
		log.info("{}", IDUtil.generatorSnowflakeIDShort());
	}

}
