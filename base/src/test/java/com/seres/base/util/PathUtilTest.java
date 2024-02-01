package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author ：liyu
 * @date ：2022/9/9 9:45
 */
@Slf4j
public class PathUtilTest {

	@Test
	public void testPath(){
		System.out.println("1=" + PathUtil.getPath("E:", "/a/", "1.txt"));
		System.out.println("2=" + PathUtil.getPath("E:/a", "/b/", "1.txt"));
		System.out.println("3=" + PathUtil.getPath("a/1.txt"));
		System.out.println("4=" + PathUtil.getPath("a", "1.txt"));
		System.out.println("5=" + PathUtil.getPath("", "a/b/", "/c/", "1.txt"));
		System.out.println("6=" + PathUtil.getPath("/", "/a/b/", "/c/", "1.txt"));
		System.out.println("7=" + PathUtil.getPath("", "/a/b/", "/c/", "1.txt"));
		System.out.println("8=" + PathUtil.getPath("", "a/b", "c", "1.txt"));
		System.out.println("9=" + PathUtil.getPath("/a", "b/c", "d", "1.txt"));
		System.out.println("10=" + PathUtil.getPath("/a", "/b/c", "d", "1.txt"));
	}

	@Test
	public void testParent(){
		System.out.println("1=" + PathUtil.getParent("E:", "/a/", "1.txt"));
		System.out.println("2=" + PathUtil.getParent("E:/a", "/b/", "1.txt"));
		System.out.println("3=" + PathUtil.getParent("a/1.txt"));
		System.out.println("4=" + PathUtil.getParent("a", "1.txt"));
		System.out.println("5=" + PathUtil.getParent("", "a/b/", "/c/", "1.txt"));
		System.out.println("6=" + PathUtil.getParent("/", "/a/b/", "/c/", "1.txt"));
		System.out.println("7=" + PathUtil.getParent("", "/a/b/", "/c/", "1.txt"));
		System.out.println("8=" + PathUtil.getParent("", "a/b", "c", "1.txt"));
		System.out.println("9=" + PathUtil.getParent("/a", "b/c", "d", "1.txt"));
	}

	@Test
	public void testFileName(){
		System.out.println("1=" + PathUtil.getFileName("E:", "/a/", "1.txt"));
		System.out.println("2=" + PathUtil.getFileName("E:/a", "/b/", "1.txt"));
		System.out.println("3=" + PathUtil.getFileName("a/1.txt"));
		System.out.println("4=" + PathUtil.getFileName("a", "1.txt"));
		System.out.println("5=" + PathUtil.getFileName("", "a/b/", "/c/", "1.txt"));
		System.out.println("6=" + PathUtil.getFileName("/", "/a/b/", "/c/", "1.txt"));
		System.out.println("7=" + PathUtil.getFileName("", "/a/b/", "/c/", "1.txt"));
		System.out.println("8=" + PathUtil.getFileName("", "a/b", "c", "1.txt"));
		System.out.println("9=" + PathUtil.getFileName("/a", "b/c", "d", "1.txt"));
	}

}
