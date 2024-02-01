package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class DateTimeUtilTest {

	@Test
	public void test1(){
		log.info(DateTimeUtil.getBeforeDay(1));
		log.info(DateTimeUtil.getAfterDay(1));
		log.info(DateTimeUtil.getBeforeMinute(10));
		log.info(DateTimeUtil.getBeforeMinute(120));
		log.info(DateTimeUtil.getAfterMinute(10));
		log.info(DateTimeUtil.getAfterMinute(120));
	}

	@Test
	public void test2(){
		log.info("--{}", System.currentTimeMillis());
		log.info("--{}", DateTimeUtil.currentTimeMillis());
		log.info(DateTimeUtil.current16HexTimeMillis());
		log.info(DateTimeUtil.current32HexTimeMillis());
		log.info(DateTimeUtil.current62HexTimeMillis());

		log.info("--{}", DateTimeUtil.currentTimeMicros());
		log.info(DateTimeUtil.current16HexTimeMicros());
		log.info(DateTimeUtil.current32HexTimeMicros());
		log.info(DateTimeUtil.current62HexTimeMicros());
	}

	@Test
	public void test3(){
		log.info(DateTimeUtil.getBeforeMonthFirstDay(1));
		log.info(DateTimeUtil.currentMonthFirstDay());
		log.info(DateTimeUtil.getAfterMonthFirstDay(1));
	}

	@Test
	public void test4(){
		Date date = new Date();
		log.info(DateTimeUtil.toString(date));
		log.info(DateTimeUtil.format(date));
		log.info(DateTimeUtil.formatDateTime(date));
		log.info(DateTimeUtil.formatDateTime14(date));
		log.info(DateTimeUtil.formatDate(date));
		log.info(DateTimeUtil.formatTime(date));
	}

	@Test
	public void test5(){
		Date date = new Date();
		log.info(DateTimeUtil.formatChineseDate(date, false, true));
		log.info(DateTimeUtil.formatChineseDate(date, true, false));
		log.info(DateTimeUtil.getChineseZodiac(2023));
		log.info("{}", DateTimeUtil.toDate("20230404141257"));
	}

}
