package com.seres.base.util.word;

import lombok.Data;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * word生成器
 * @author ：liyu
 * @date ：2022/10/8 13:27
 */
@Data
public abstract class WordGenerator {

	/**
	 * 模板文件路径
	 */
	private String wordTemplate = WordGenerator.class.getClassLoader().getResource("").getPath() + "doc-template.docx";

	/**
	 * 对word模板的具体操作，一般情况下只需调用 WordUtil中的方法进行组合即可，图表处理建议先替换再插入
	 * @param document
	 * @param vo
	 */
	protected abstract void handle(XWPFDocument document, WordVo vo) throws Exception;

	/**
	 * 往指定输出流中写入数据，记得调用方关闭output流
	 * @param out
	 * @param vo
	 * @throws Exception
	 */
	public void generator(OutputStream out, WordVo vo) throws Exception{
		//获取模板docx解析对象
		XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(wordTemplate));
		// 具体对模板的操作步骤
		handle(document, vo);
		//生成新的word
		document.write(out);
	}

	/**
	 * 根据模板创建word并保存至指定目录
	 * @param outPath 如：E:\temp\poi\report.docx
	 * @param vo
	 * @return
	 */
	public void generator(String outPath, WordVo vo) throws Exception{
		//生成新的word
		File file = new File(outPath);
		try (FileOutputStream stream = new FileOutputStream(file)){
			generator(stream, vo);
		}catch (Exception e){
			throw e;
		}
	}
}
