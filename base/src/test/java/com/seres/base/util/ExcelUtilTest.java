package com.seres.base.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：liyu
 * @date ：2022/10/21 13:23
 */
public class ExcelUtilTest {

	@Test
	public void test() throws Exception{
		ExcelTestVo et1 = new ExcelTestVo("zs", 23);
		ExcelTestVo et2 = new ExcelTestVo("ls", 25);
		ExcelTestVo et3 = new ExcelTestVo("王五", 28);
		List<ExcelTestVo> list = new ArrayList<>();
		list.add(et1);
		list.add(et2);
		list.add(et3);
		ExcelUtil.write2Excel("E:\\temp\\easyexcel.xlsx", "list", list, ExcelTestVo.class);
	}
}

@Data
@AllArgsConstructor
class ExcelTestVo {
	@ExcelProperty("姓名")
	private String name;

	@ExcelProperty("年龄")
	private Integer age;
}
