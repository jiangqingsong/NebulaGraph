package com.seres.base.util;

import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * @author ：liyu
 * @date ：2024/1/23 13:44
 */
public class PathUtil {

	public static final String SEPARATOR = FileSystems.getDefault().getSeparator();

	/**
	 * 拼接路径
	 * @param first /a
	 * @param more b/1.txt
	 * @return 如：/a/b/1.txt
	 */
	public static String join(String first, String... more){
		return getPath(first, more);
	}

	/**
	 * 获取路径
	 * @param first /a
	 * @param more b/1.txt
	 * @return 如：/a/b/1.txt
	 */
	public static String getPath(String first, String... more){
		return Paths.get(first, more).toString();
	}

	/**
	 * 获取父路径
	 * @param first /a
	 * @param more b/1.txt
	 * @return 如：/a/b
	 */
	public static String getParent(String first, String... more){
		return Paths.get(first, more).getParent().toString();
	}

	/**
	 * 获取最后一级文件名（文件或目录）
	 * @param first /a
	 * @param more b/1.txt
	 * @return 如：1.txt
	 */
	public static String getFileName(String first, String... more){
		return Paths.get(first, more).getFileName().toString();
	}

}
