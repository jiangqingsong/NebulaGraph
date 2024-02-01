package com.seres.base.util;

import cn.hutool.core.util.IdUtil;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author ：liyu
 * @date ：2022/10/31 17:37
 */
public class IDUtil {

	/**
	 * 62进制不带-的短uuid，如：4vDvJD3584rW9LE7lFEhI1I
	 * @return
	 */
	public static String generatorUUIDShort(){
		return Arrays.stream(generatorUUID36().split("-")).map(hex->{
			long l = Long.parseLong(hex, 16);
			return StringUtil.getRadixString(l, 62);
		}).collect(Collectors.joining());
	}

	/**
	 * 32位不带-的小写uuid，如：981efff0b07e4f97ad27bf53e59ce422
	 * @return
	 */
	public static String generatorUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 32位不带-的大些UUID，如：981EFFF0B07E4F97AD27BF53E59CE422
	 * @return
	 */
	public static String generatorUUIDUpper(){
		return generatorUUID().toUpperCase();
	}

	/**
	 * 36位带-的小写uuid，如：981efff0-b07e-4f97-ad27-bf53e59ce422
	 * @return
	 */
	public static String generatorUUID36(){
		return UUID.randomUUID().toString();
	}

	/**
	 * 36位带-的大些UUID，如：981EFFF0-B07E-4F97-AD27-BF53E59CE422
	 */
	public static String generatorUUIDUpper36(){
		return generatorUUID36().toUpperCase();
	}

	/**
	 * 生成雪花ID（Long型），如：1643182737123225600
	 */
	public static Long generatorSnowflakeID(){
		return IdUtil.getSnowflake().nextId();
	}

	/**
	 * 生成雪花ID（String型），如：1643182737123225600
	 */
	public static String generatorSnowflakeIDStr(){
		return IdUtil.getSnowflake().nextIdStr();
	}

	/**
	 * 生成雪花62进制短ID，如：1XqA7Mrbing
	 */
	public static String generatorSnowflakeIDShort(){
		return  StringUtil.getRadixString(generatorSnowflakeID(), 62);
	}

}
