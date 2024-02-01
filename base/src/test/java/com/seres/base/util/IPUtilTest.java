package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class IPUtilTest {

	@Test
	public void test(){
		log.info("ips={}", IPUtil.getLocalIps());
		log.info("ipv4s={}", IPUtil.getLocalIpV4s());
		log.info("ipv6s={}", IPUtil.getLocalIpV6s());
	}

}
