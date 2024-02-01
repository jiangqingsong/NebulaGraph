package com.seres.base.tool;

import com.seres.base.BaseErrCode;
import com.seres.base.exception.AppException;
import com.seres.base.util.FileUtil;
import io.minio.*;
import io.minio.messages.Item;
import io.minio.messages.Tags;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：liyu
 * @date ：2023/1/30 14:56
 * https://blog.csdn.net/qq_43437874/article/details/120849494
 */
@Slf4j
public class MinioTest {

	public static final String BUCKET = "liyu";
	public static MinioClient client;

	@BeforeAll
	public static void init(){
		client = MinioClient.builder().endpoint("http://192.168.5.94:9000")
				.credentials("M08C9FTS95EDYYOY929R", "qKuT0UUE9L5KkIxHHwnvP0KjcfsMspucLOnvKXW5").build();
	}

	@Test
	public void ls() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
				.withZone(ZoneId.systemDefault());
		Iterable<Result<Item>> ls = client.listObjects(ListObjectsArgs.builder()
				.bucket(BUCKET)
				.recursive(true) // 递归
//				.startAfter("dpi.pcap") // 条件查询，指定文件之后（按ls列表中的顺序）的
				.prefix("test") // 条件查询，指定key的前缀
				.build());
		ls.forEach(a -> {
			try {
				Item item = a.get();
				log.info("{} {}", item.lastModified().format(formatter), item.objectName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void upload() {
		String fileName = "/test/minio.txt";
		String localFilePath =	"E:\\temp\\minio\\minio.txt";
		log.info("上传文件至minio：{}", fileName);
		try {
			client.uploadObject(UploadObjectArgs.builder()
					.bucket(BUCKET)
					.object(fileName)
					.filename(localFilePath)
					.build());
		} catch (Exception e) {
			log.error("上传文件至minio失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_UPLOAD_ERR);
		}
	}

	@Test
	public void save() {
		String fileName = "/test/minio.txt";
		String localFilePath =	"E:\\temp\\minio\\save.txt";
		try (InputStream in = get(fileName)) {
			FileUtil.save(in, localFilePath);
		}catch (Exception e){
			log.error("获取minio文件流并写入本地文件失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
		}
	}

	/**
	 * 获取minio文件流
	 * @param fileName 远程minio文件名，如：test.png，支持目录结构，如：/log/test.txt
	 * @return 文件流
	 */
	public InputStream get(String fileName){
		log.info("获取minio文件：{}", fileName);
		try {
			return client.getObject(GetObjectArgs.builder()
					.bucket(BUCKET)
					.object(fileName)
					.build());
		}catch (Exception e){
			log.error("获取minio文件流失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_DOWNLOAD_ERR);
		}
	}

	@Test
	public void addTags() {
		String fileName = "/tags.txt";
		String localFilePath =	"E:\\temp\\minio\\minio.txt";
		log.info("上传文件并添加标签至minio：{}", fileName);
		try {
			Map<String, String> map = new HashMap<>();
			map.put("type", "1");
			map.put("org", "brd");
			client.uploadObject(UploadObjectArgs.builder()
					.bucket(BUCKET)
					.object(fileName)
					.tags(map)
					.filename(localFilePath)
					.build());
		} catch (Exception e) {
			log.error("上传文件并添加标签至minio失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_UPLOAD_ERR);
		}
	}

	@Test
	public void setTags() {
		String fileName = "/tags.txt";
		log.info("修改文件标签至minio：{}", fileName);
		try {
			Map<String, String> map = new HashMap<>();
			map.put("type", "2"); // 修改
			map.put("add", "put"); // 新增
			client.setObjectTags(SetObjectTagsArgs.builder()
					.bucket(BUCKET)
					.object(fileName)
					.tags(map)
					.build());
		} catch (Exception e) {
			log.error("修改文件标签至minio失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_UPLOAD_ERR);
		}
	}

	@Test
	public void getTags() throws Exception{
		String fileName = "/tags.txt";
		Tags ss = client.getObjectTags(GetObjectTagsArgs.builder()
				.bucket(BUCKET)
				.object(fileName)
				.build());
		log.info("{} tags={}",fileName, ss.get());
	}

	@Test
	public void addStat() {
		String fileName = "/stat.txt";
		String localFilePath =	"E:\\temp\\minio\\minio.txt";
		log.info("上传文件并添加元数据至minio：{}", fileName);
		try {
			Map<String, String> map = new HashMap<>();
			map.put("type", "1");
			map.put("org", "brd");
			client.uploadObject(UploadObjectArgs.builder()
					.bucket(BUCKET)
					.object(fileName)
					.userMetadata(map)
					.filename(localFilePath)
					.build());
		} catch (Exception e) {
			log.error("上传文件并添加元数据失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_UPLOAD_ERR);
		}
	}

	@Test
	public void getStat(){
		String fileName = "/stat.txt";
		try {
			StatObjectResponse stat = client.statObject(StatObjectArgs.builder()
					.bucket(BUCKET)
					.object(fileName)
					.build());
            log.info("stat={}", stat);
            log.info("metaData={}", stat.userMetadata());
            log.info("contentType={}", stat.contentType());
		}catch (Exception e){
			log.error("获取minio文件stat信息失败：{}", fileName, e);
			throw new AppException(BaseErrCode.FILE_OPT_ERR);
		}
	}

}
