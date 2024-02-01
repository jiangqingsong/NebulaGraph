package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author ：liyu
 * @date ：2022/9/9 11:34
 */
@Slf4j
public class CompressUtilTest {

	@Test
	public void tar() {
		String out = "E:\\temp\\tar\\test.tar";
		CompressUtil.tar(out, "E:\\temp\\tar\\t1.txt", "E:\\temp\\tar\\t2.txt");
		File fo = new File(out);
		Assertions.assertTrue(fo.exists());
	}
	@Test
	public void unTar() {
		String out = "E:\\temp\\tar\\unTar";
		CompressUtil.unTar("E:\\temp\\tar\\test.tar", out);
		File fo = new File(out);
		Assertions.assertTrue(fo.list().length > 1);
	}
	@Test
	public void zip() {
		String out = "E:\\temp\\tar\\test.zip";
		CompressUtil.zip(out, "E:\\temp\\tar\\t1.txt", "E:\\temp\\tar\\t2.txt");
		File fo = new File(out);
		Assertions.assertTrue(fo.exists());
	}
	@Test
	public void unZip() {
		String out = "E:\\temp\\tar\\unZip";
		CompressUtil.unZip("E:\\temp\\tar\\test.zip", out);
		File fo = new File(out);
		Assertions.assertTrue(fo.list().length > 1);

	}
	@Test
	public void tarGz() {
		String out = "E:\\temp\\tar\\test.tar.gz";
		CompressUtil.tarGz(out, "E:\\temp\\tar\\t1.txt", "E:\\temp\\tar\\t2.txt");
		File fo = new File(out);
		Assertions.assertTrue(fo.exists());
	}
	@Test
	public void unTarGz() {
		String out = "E:\\temp\\tar\\unTarGz";
		CompressUtil.unTarGz("E:\\temp\\tar\\test.tar.gz", out);
		File fo = new File(out);
		Assertions.assertTrue(fo.list().length > 1);
	}

	//////////////其他压缩//////////////

	@Test
	public void tarGzToInputStream() throws Exception{
		String out = "E:\\temp\\tar\\in.tar.gz";
		InputStream in = new FileInputStream("E:\\temp\\tar\\t1.txt");
		InputStream gin = CompressUtil.tarGzToInputStream("123456", in);
		FileUtil.save(gin, out);
		File fo = new File(out);
		Assertions.assertTrue(fo.exists());
	}

	@Test
	public void tarGzFromStringToFile() {
		String out = "E:\\temp\\tar\\string.tar.gz";
		CompressUtil.tarGzFromString(out, "a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>");
		File fo = new File(out);
		Assertions.assertTrue(fo.exists());
	}

	@Test
	public void tarGzFormStringToByte() {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>";
		byte[] bs = CompressUtil.tarGzFromStringToByte(str);
		log.info("byteSize={}，gz={}", bs.length, bs);
	}

	@Test
	public void tarGzFormStringToString() {
		String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>";
		String gz = CompressUtil.tarGzFromStringToString(src);
		log.info("byteSize={}，gz={}", gz.getBytes().length, gz);
	}

	//////////////其他解压//////////////

	@Test
	public void unTarGzToString() {
		List<String> list = CompressUtil.unTarGzToString("E:\\temp\\tar\\pc.tar.gz");
		list.forEach(l->{
			log.info(l);
		});
	}

	@Test
	public void unTarGzFromByteToString() throws Exception{
		byte[] bs = Files.readAllBytes(Paths.get("E:\\temp\\tar\\pc.tar.gz"));
		List<String> list = CompressUtil.unTarGzFromByteToString(bs);
		list.forEach(l->{
			log.info(l);
		});
	}

	@Test
	public void unTarGzFromStringToString() throws Exception{
		byte[] bs = Files.readAllBytes(Paths.get("E:\\temp\\tar\\pc.tar.gz"));
		String str = new String(bs, StandardCharsets.ISO_8859_1);  // 此处必须为ISO_8859_1编码
		List<String> list = CompressUtil.unTarGzFromStringToString(str);
		list.forEach(l->{
			log.info(l);
		});
	}

	//////////////其他综合//////////////

	@Test
	public void tarGzAndUnTarGz() {
		CompressUtil.tarGzFromString("E:\\temp\\tar\\string.tar.gz", "a.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>");
		List<String> list = CompressUtil.unTarGzToString("E:\\temp\\tar\\string.tar.gz");
		list.forEach(l->{
			log.info(l);
		});
	}

	@Test
	public void tarGzAndUnTarGz2() {
		String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>";
		byte[] bs = CompressUtil.tarGzFromStringToByte(src);
		log.info("byteSize={}，gz={}", bs.length, bs);
		List<String> list = CompressUtil.unTarGzFromByteToString(bs);
		list.forEach(l-> {
			log.info(l);
		});
	}

	@Test
	public void tarGzAndUnTarGz3() {
		String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>";
		String gz = CompressUtil.tarGzFromStringToString(src);
		log.info("byteSize={}，gz={}", gz.getBytes(StandardCharsets.ISO_8859_1).length, gz);
		List<String> list = CompressUtil.unTarGzFromStringToString(gz);
		list.forEach(l-> {
			log.info(l);
		});
	}

	@Test
	public void gzipAndUnGzip() {
		String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>";
		byte[] bs = CompressUtil.gzip(src);
		log.info("byteSize={}，gz={}", bs.length, bs);
		String str = CompressUtil.unGzipToString(bs);
		log.info(str);
	}

	@Test
	public void gzipAndUnGzip2() {
		String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><project>测试</project>";
		String gz = CompressUtil.gzipToString(src);
		log.info("byteSize={}，gz={}", gz.getBytes(StandardCharsets.ISO_8859_1).length, gz);
		String str = CompressUtil.unGzipToString(gz);
		log.info(str);
	}

}
