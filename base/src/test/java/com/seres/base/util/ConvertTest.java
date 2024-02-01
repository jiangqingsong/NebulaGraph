package com.seres.base.util;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class ConvertTest {

	@Test
	public void test(){
		log.info("strToUnicode={}", Convert.strToUnicode("你好"));
		log.info("unicodeToStr={}", Convert.unicodeToStr("\\u4f60\\u597d"));
		log.info("toDate={}", Convert.toDate("2023-04-04 12:15:11"));
		log.info("toDate={}", Convert.toDate(System.currentTimeMillis()));
		log.info("toDate={}", DateTimeUtil.formatDateTime(Convert.toDate(System.currentTimeMillis())));
	}

	@Test
	public void test2(){
		String [] sz = {"11", "22"};
		List<?> list1 = Convert.toList(sz);
		List<String> list2 = Convert.toList(String.class, sz);
		log.info("toList={}, {}", list1, list2);
		String[] arr1 = Convert.toStrArray(list2);
		log.info("toStrArray={}, {}", arr1);
		String[] arr2 = list2.toArray(new String[2]);
		log.info("toArray={}, {}", arr2);
		log.info("=====list类型转换=====");
		List<Integer> ii = Convert.toList(Integer.class, sz);
		log.info("toList={}", ii);

	}

}
