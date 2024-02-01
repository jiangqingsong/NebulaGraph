package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class FileUtilTest {

	@Test
	public void test(){
		Assertions.assertEquals("E:\\temp\\bb", Paths.get("E:\\temp", "bb").toString());
		Assertions.assertEquals("E:\\temp\\bb", Paths.get("E:\\temp\\", "bb").toString());
		Assertions.assertEquals("E:\\temp\\bb", Paths.get("E:\\temp", "\\bb").toString());
		Assertions.assertEquals("E:\\temp\\bb", Paths.get("E:\\temp\\", "\\bb").toString());
	}

}
