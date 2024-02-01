package com.seres.base.util;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

/**
 * 打包/压缩操作工具类
 */
@Slf4j
public class CompressUtil {

	private static final String NOT_FILE = "不是常规文件或文件不存在：{}";
	private static final String DECOMPRESS_ERR = "解压失败：{}";
	private static final String COMPRESS_ERR = "压缩失败：{}";

	/**
	 * tar打包，打包本地文件至本地压缩文件
	 * @param outTarPath 打包后输出文件全路径
	 * @param srcPaths 被打包的原始文件列表（全路径）
	 */
	public static void tar(String outTarPath, String ... srcPaths) {
		// 打包结果文件
		Path output = Paths.get(outTarPath);
		//OutputStream输出流、BufferedOutputStream缓冲输出流
		//TarArchiveOutputStream打tar包输出流
		try (OutputStream fOut = Files.newOutputStream(output);
			 BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
			 TarArchiveOutputStream tOut = new TarArchiveOutputStream(buffOut)) {
			// 使文件名支持超过 100 个字节
			tOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
			for(String src: srcPaths){
				Path path = Paths.get(src);
				//该文件不是目录或者符号链接
				if (!Files.isRegularFile(path)) {
					log.error(NOT_FILE, path);
					throw new AppException(BaseErrCode.NOT_FILE);
				}
				//将该文件放入tar包
				TarArchiveEntry tarEntry = new TarArchiveEntry(path.toFile(),
						path.getFileName().toString());

				tOut.putArchiveEntry(tarEntry);
				Files.copy(path, tOut);
				tOut.closeArchiveEntry();
			}
			//for循环完成之后，finish-tar包输出流
			tOut.finish();
			buffOut.flush();
			fOut.flush();
		}catch (Exception e){
			log.error("打包失败：{}", outTarPath, e);
			throw new AppException(BaseErrCode.COMPRESS_ERR);
		}
	}

	/**
	 * tar解包，解包本地压缩文件至本地目录
	 * @param inTarPath 待解包的tar文件全路径
	 * @param outDir 解包后的目录
	 */
	public static void unTar(String inTarPath, String outDir) {
		//解压文件
		Path source = Paths.get(inTarPath);
		//解压到哪
		Path target = Paths.get(outDir);
		if (Files.notExists(source)) {
			log.error(NOT_FILE, source);
			throw new AppException(BaseErrCode.NOT_FILE);
		}
		//InputStream输入流，将tar读取到内存并操作
		//BufferedInputStream缓冲输入流
		//TarArchiveInputStream解tar包输入流
		try (InputStream fi = Files.newInputStream(source);
			 BufferedInputStream bi = new BufferedInputStream(fi);
			 TarArchiveInputStream ti = new TarArchiveInputStream(bi)) {
			ArchiveEntry entry;
			while ((entry = ti.getNextEntry()) != null) {
				//获取解压文件目录，并判断文件是否损坏
				Path newPath = entryCheck(entry.getName(), target);
				if (entry.isDirectory()) {
					//创建解压文件目录
					Files.createDirectories(newPath);
				} else {
					//再次校验解压文件目录是否存在
					Path parent = newPath.getParent();
					if (parent != null) {
						if (Files.notExists(parent)) {
							Files.createDirectories(parent);
						}
					}
					// 将解压文件输入到TarArchiveInputStream，输出到磁盘newPath目录
					Files.copy(ti, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}catch (Exception e){
			log.error(DECOMPRESS_ERR, inTarPath, e);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
	}

	/**
	 * zip压缩，压缩本地文件至本地压缩文件
	 * @param outZipPath 压缩后输出文件全路径
	 * @param srcPaths 被压缩的原始文件列表（全路径）
	 */
	public static void zip(String outZipPath, String ... srcPaths) {
		// 压缩结果文件
		Path output = Paths.get(outZipPath);
		//OutputStream输出流、BufferedOutputStream缓冲输出流
		//ZipOutputStream压缩输出流
		try (OutputStream fOut = Files.newOutputStream(output);
			 BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
			 ZipOutputStream zOut = new ZipOutputStream(buffOut)) {
			for(String src: srcPaths){
				Path path = Paths.get(src);
				//该文件不是目录或者符号链接
				if (!Files.isRegularFile(path)) {
					log.error(NOT_FILE, path);
					throw new AppException(BaseErrCode.NOT_FILE);
				}
				ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
				zOut.putNextEntry(zipEntry);
				Files.copy(path, zOut);
				// 结束当前ZipEntry
				zOut.closeEntry();
			}
			//for循环完成之后，finish-tar包输出流
			zOut.finish();
			buffOut.flush();
			fOut.flush();
		}catch (Exception e){
			log.error("打包失败：{}", outZipPath, e);
			throw new AppException(BaseErrCode.COMPRESS_ERR);
		}
	}

	/**
	 * zip解压，解压本地压缩文件至本地目录
	 * @param inZipPath 待解压的zip文件全路径
	 * @param outDir 解压后的目录
	 */
	public static void unZip(String inZipPath, String outDir) {
		//解压文件
		Path source = Paths.get(inZipPath);
		//解压到哪
		Path target = Paths.get(outDir);
		if (Files.notExists(source)) {
			log.error(NOT_FILE, source);
			throw new AppException(BaseErrCode.NOT_FILE);
		}
		//InputStream输入流，将zip读取到内存并操作
		//BufferedInputStream缓冲输入流
		//ZipInputStream解zip包输入流
		try (InputStream fi = Files.newInputStream(source);
			 BufferedInputStream bi = new BufferedInputStream(fi);
			 ZipInputStream ti = new ZipInputStream(bi)) {
			ZipEntry entry;
			while ((entry = ti.getNextEntry()) != null) {
				//获取解压文件目录，并判断文件是否损坏
				Path newPath = entryCheck(entry.getName(), target);
				if (entry.isDirectory()) {
					//创建解压文件目录
					Files.createDirectories(newPath);
				} else {
					//再次校验解压文件目录是否存在
					Path parent = newPath.getParent();
					if (parent != null) {
						if (Files.notExists(parent)) {
							Files.createDirectories(parent);
						}
					}
					// 将解压文件输入到TarArchiveInputStream，输出到磁盘newPath目录
					Files.copy(ti, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}catch (Exception e){
			log.error(DECOMPRESS_ERR, inZipPath, e);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
	}

	/**
	 * tar.gz压缩，压缩本地文件至本地压缩文件
	 * @param outGzPath 压缩后输出文件全路径
	 * @param srcPaths 被压缩的原始文件列表（全路径）
	 */
	public static void tarGz(String outGzPath, String ... srcPaths){
		// 压缩结果文件
		Path output = Paths.get(outGzPath);
		//OutputStream输出流、BufferedOutputStream缓冲输出流
		//GzipCompressorOutputStream是gzip压缩输出流
		//TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
		try (OutputStream fOut = Files.newOutputStream(output);
			 BufferedOutputStream buffOut = new BufferedOutputStream(fOut);
			 GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
			 TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {
			// 使文件名支持超过 100 个字节
			tOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
			for(String src: srcPaths){
				Path path = Paths.get(src);
				//该文件不是目录或者符号链接
				if (!Files.isRegularFile(path)) {
					log.error(NOT_FILE, path);
					throw new AppException(BaseErrCode.NOT_FILE);
				}
				//将该文件放入tar包，并执行gzip压缩
				TarArchiveEntry tarEntry = new TarArchiveEntry(path.toFile(),
						path.getFileName().toString());

				tOut.putArchiveEntry(tarEntry);
				Files.copy(path, tOut);
				tOut.closeArchiveEntry();
			}
			//for循环完成之后，finish-tar包输出流
			tOut.finish();
			gzOut.finish();
			buffOut.flush();
			fOut.flush();
		}catch (Exception e){
			log.error(COMPRESS_ERR, outGzPath, e);
			throw new AppException(BaseErrCode.COMPRESS_ERR);
		}
	}

	/**
	 * tar.gz解压，解压本地压缩文件至本地目录
	 * @param inGzPath 待解压的tar.gz文件全路径
	 * @param outDir 解压后的目录
	 */
	public static void unTarGz(String inGzPath, String outDir) {
		//解压文件
		Path source = Paths.get(inGzPath);
		//解压到哪
		Path target = Paths.get(outDir);
		if (Files.notExists(source)) {
			log.error(NOT_FILE, source);
			throw new AppException(BaseErrCode.NOT_FILE);
		}
		//InputStream输入流，以下四个流将tar.gz读取到内存并操作
		//BufferedInputStream缓冲输入流
		//GzipCompressorInputStream解压输入流
		//TarArchiveInputStream解tar包输入流
		try (InputStream fi = Files.newInputStream(source);
			 BufferedInputStream bi = new BufferedInputStream(fi);
			 GzipCompressorInputStream gzi = new GzipCompressorInputStream(bi);
			 TarArchiveInputStream ti = new TarArchiveInputStream(gzi)) {
			ArchiveEntry entry;
			while ((entry = ti.getNextEntry()) != null) {
				//获取解压文件目录，并判断文件是否损坏
				Path newPath = entryCheck(entry.getName(), target);
				if (entry.isDirectory()) {
					//创建解压文件目录
					Files.createDirectories(newPath);
				} else {
					//再次校验解压文件目录是否存在
					Path parent = newPath.getParent();
					if (parent != null) {
						if (Files.notExists(parent)) {
							Files.createDirectories(parent);
						}
					}
					// 将解压文件输入到TarArchiveInputStream，输出到磁盘newPath目录
					Files.copy(ti, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}catch (Exception e){
			log.error(DECOMPRESS_ERR, inGzPath, e);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
	}

	/////////////////

	/**
	 * tar.gz压缩，压缩字符串内容至本地压缩文件
	 * @param outGzPath 压缩后输出文件全路径
	 * @param entryName 压缩文件中的文件名
	 * @param content 被压缩的内容
	 * @return 压缩后的字节数组
	 */
	public static void tarGzFromString(String outGzPath, String entryName, String content){
		// 压缩结果文件
		Path output = Paths.get(outGzPath);
		//OutputStream输出流、BufferedOutputStream缓冲输出流
		//GzipCompressorOutputStream是gzip压缩输出流
		//TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
		try (OutputStream fout = Files.newOutputStream(output);
			 BufferedOutputStream buffOut = new BufferedOutputStream(fout);
			 GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
			 TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {
			// 使文件名支持超过 100 个字节
			tOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
			//将该文件放入tar包，并执行gzip压缩
			TarArchiveEntry tarEntry = new TarArchiveEntry(entryName);
			byte[] bs = content.getBytes();
			tarEntry.setSize(bs.length);
			tOut.putArchiveEntry(tarEntry);
			tOut.write(bs);
			tOut.closeArchiveEntry();
			tOut.finish();
			gzOut.finish();
			buffOut.flush();
			fout.flush();
		}catch (Exception e){
			log.error(COMPRESS_ERR, content, e);
			throw new AppException(BaseErrCode.COMPRESS_ERR);
		}
	}

	/**
	 * tar.gz压缩，压缩字符串并直接返回压缩后的字符串
	 * @param content 被压缩的内容
	 * @return 压缩后的字符串
	 */
	public static String tarGzFromStringToString(String content){
		return new String(tarGzFromStringToByte(content), StandardCharsets.ISO_8859_1);
	}

	/**
	 * tar.gz压缩，压缩输入流并返回压缩后的输入流
	 * @param entryName 压缩文件内的文件的名称
	 * @param inputStream 被压缩的输入流
	 * @return 压缩后的输入流
	 */
	public static InputStream tarGzToInputStream(String entryName, InputStream inputStream){
		byte[] bs = IOUtil.toByteArray(inputStream);
		return new ByteArrayInputStream(tarGzFromByteToByte(entryName, bs));
	}

	/**
	 * tar.gz压缩，压缩字符串并直接返回压缩后的字节数组
	 * @param content 被压缩的内容
	 * @return 压缩后的字节数组
	 */
	public static byte[] tarGzFromStringToByte(String content){
		return tarGzFromByteToByte(null, content.getBytes());
	}

	/**
	 * tar.gz压缩，压缩字节数组并直接返回压缩后的字节数组
	 * @param entryName 压缩文件内的文件的名称
	 * @param bs 被压缩的字节数组
	 * @return 压缩后的字节数组
	 */
	public static byte[] tarGzFromByteToByte(String entryName, byte[] bs){
		if(StringUtil.isEmpty(entryName)){
			entryName = "temp";
		}
		// 压缩结果文件
		//ByteArrayOutputStream输出流、BufferedOutputStream缓冲输出流
		//GzipCompressorOutputStream是gzip压缩输出流
		//TarArchiveOutputStream打tar包输出流（包含gzip压缩输出流）
		try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
			 BufferedOutputStream buffOut = new BufferedOutputStream(bout);
			 GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(buffOut);
			 TarArchiveOutputStream tOut = new TarArchiveOutputStream(gzOut)) {
			// 使文件名支持超过 100 个字节
			tOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
			//将该文件放入tar包，并执行gzip压缩
			TarArchiveEntry tarEntry = new TarArchiveEntry(entryName);
			tarEntry.setSize(bs.length);
			tOut.putArchiveEntry(tarEntry);
			tOut.write(bs);
			tOut.closeArchiveEntry();
			tOut.finish();
			gzOut.finish();
			buffOut.flush();
			bout.flush();
			return bout.toByteArray();
		}catch (Exception e){
			log.error(COMPRESS_ERR, entryName, e);
			throw new AppException(BaseErrCode.COMPRESS_ERR);
		}
	}

	/////////////////

	/**
	 * tar.gz解压，解压本地压缩文件并直接返回解压后字符串内容，支持压缩文件中的目录
	 * @param inGzPath 待解压的tar.gz文件全路径
	 * @return 解压后的每个文件字符串内容
	 */
	public static List<String> unTarGzToString(String inGzPath) {
		List<String> result = new ArrayList<>();
		//解压文件
		Path source = Paths.get(inGzPath);
		//BufferedInputStream缓冲输入流
		//GzipCompressorInputStream解压输入流
		//TarArchiveInputStream解tar包输入流
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
			 InputStream fi = Files.newInputStream(source);
			 BufferedInputStream bi = new BufferedInputStream(fi);
			 GzipCompressorInputStream gzi = new GzipCompressorInputStream(bi);
			 TarArchiveInputStream ti = new TarArchiveInputStream(gzi)) {
			ArchiveEntry entry;
			while ((entry = ti.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					out.reset();
					IOUtil.copy(ti, out);
					result.add(out.toString());
				}
			}
		}catch (Exception e){
			log.error(DECOMPRESS_ERR, inGzPath, e);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
		return result;
	}

	/**
	 * tar.gz解压，解压压缩字节数组并直接返回解压后字符串内容
	 * @param gzContent tar.gz压缩后的字节数组
	 * @return 解压后字符串内容
	 */
	public static List<String> unTarGzFromByteToString(byte[] gzContent) {
		List<String> result = new ArrayList<>();
		//BufferedInputStream缓冲输入流
		//GzipCompressorInputStream解压输入流
		//TarArchiveInputStream解tar包输入流
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
			 ByteArrayInputStream in = new ByteArrayInputStream(gzContent);
			 BufferedInputStream bi = new BufferedInputStream(in);
			 GzipCompressorInputStream gzi = new GzipCompressorInputStream(bi);
			 TarArchiveInputStream ti = new TarArchiveInputStream(gzi)) {
			ArchiveEntry entry;
			while ((entry = ti.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					out.reset();
					IOUtil.copy(ti, out);
					result.add(out.toString());
				}
			}
		}catch (Exception e){
			log.error("解压失败，待解压字节长度：{}", gzContent.length, e);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
		return result;
	}

	/**
	 * tar.gz解压，解压压缩字符串（编码需为：ISO_8859_1）并直接返回解压后字符串内容
	 * @param gzContent tar.gz压缩后的字符串内容（编码需为：ISO_8859_1）
	 * @return 解压后字符串内容
	 */
	public static List<String> unTarGzFromStringToString(String gzContent) {
		// 此处必须为ISO_8859_1编码
		return unTarGzFromByteToString(gzContent.getBytes(StandardCharsets.ISO_8859_1));
	}

	/////////////////

	/**
	 * gzip压缩
	 * @param src 待压缩的字符串
	 * @return 压缩后的字符串
	 */
	public static String gzipToString(String src) {
		return new String(gzip(src), StandardCharsets.ISO_8859_1);
	}

	/**
	 * gzip压缩
	 * @param src 待压缩的字符串
	 * @return 压缩后的字节数组
	 */
	public static byte[] gzip(String src) {
		return gzip(src.getBytes());
	}

	/**
	 * gzip压缩
	 * @param src 待压缩的字节数组
	 * @return 压缩后的字节数组
	 */
	public static byte[] gzip(byte[] src) {
		// ByteArrayOutputStream输出流、BufferedOutputStream缓冲输出流
		// GZIPOutputStream压缩输出流
		try (ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			 BufferedOutputStream buffOut = new BufferedOutputStream(bOut);
			 GZIPOutputStream gOut = new GZIPOutputStream(buffOut)) {
			gOut.write(src);
			gOut.finish();
			buffOut.flush();
			bOut.flush();
			return bOut.toByteArray();
		}catch (Exception e){
			log.error(COMPRESS_ERR, src.length, e);
			throw new AppException(BaseErrCode.COMPRESS_ERR);
		}
	}

	/**
	 * gzip解压
	 * @param gz 待解压的gzip字符串
	 * @return 解压后的字符串
	 */
	public static String unGzipToString(String gz) {
		return unGzipToString(gz.getBytes(StandardCharsets.ISO_8859_1));
	}

	/**
	 * gzip解压
	 * @param gz 待解压的gzip字节数组
	 * @return 解压后的字符串
	 */
	public static String unGzipToString(byte[] gz) {
		return new String(unGzip(gz));
	}

	/**
	 * gzip解压
	 * @param gz 待解压的gzip字节数组
	 * @return 解压后的字节数组
	 */
	public static byte[] unGzip(byte[] gz) {
		// ByteArrayOutputStream输出流、BufferedOutputStream缓冲输出流
		// ByteArrayInputStream输入流，BufferedInputStream缓冲输入流
		// GZIPInputStream解gzip包输入流
		try (ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			 BufferedOutputStream buffOut = new BufferedOutputStream(bOut);
			 ByteArrayInputStream bIn = new ByteArrayInputStream(gz);
			 BufferedInputStream buffIn = new BufferedInputStream(bIn);
			 GZIPInputStream gIn = new GZIPInputStream(buffIn)) {
			IOUtil.copy(gIn, buffOut);
			buffOut.flush();
			bOut.flush();
			return bOut.toByteArray();
		}catch (Exception e){
			log.error(DECOMPRESS_ERR, gz.length, e);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
	}

	/////////////////

	/**
	 * 判断文件是否被损坏，并返回该文件的全路径
	 * @param name
	 * @param targetDir
	 * @return 解压目录
	 */
	private static Path entryCheck(String name, Path targetDir) {
		Path targetDirResolved = targetDir.resolve(name);
		Path normalizePath = targetDirResolved.normalize();
		if (!normalizePath.startsWith(targetDir)) {
			log.error("压缩文件已被损坏：{}", name);
			throw new AppException(BaseErrCode.DECOMPRESS_ERR);
		}
		return normalizePath;
	}


}
