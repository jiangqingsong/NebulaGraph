package com.seres.base.util.word;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xddf.usermodel.chart.BarGrouping;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.drawingml.x2006.chart.STDLblPos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：liyu
 * @date ：2022/9/28 16:48
 */
@Slf4j
public class WordGeneratorTest {

	private WordVo getVo(){
		WordVo vo = new WordVo();

		vo.addTextReplace("create_time", "2022-09-28 12:13:14");
		vo.addTextReplace("start_time", "2022-09-01");
		vo.addTextReplace("end_time", "2022-09-28");
		vo.addTextReplace("local", "重庆");
		vo.addTextReplace("levels", "高危,中危");

		vo.addTextReplace("gsd", "5");
		vo.addTextReplace("zcjs", "210");
		vo.addTextReplace("zxbb", "198");


		DataSource c2_1 = new DataSource();
		c2_1.setSeriesName(new String[] {"盈利额"});
		c2_1.addCategory("1月", new Number[] {18});
		c2_1.addCategory("2月", new Number[] {22});
		c2_1.addCategory("3月", new Number[] {26});
		c2_1.addCategory("4月", new Number[] {28});
		c2_1.addCategory("5月", new Number[] {20});
		c2_1.addCategory("6月", new Number[] {30});
		vo.addChartData("c2_1", c2_1);

		DataSource c2_2 = new DataSource();
		c2_2.setSeriesName(new String[] {"男", "女"});
		c2_2.addCategory("1月", new Number[] {20, 15});
		c2_2.addCategory("2月", new Number[] {22, 20});
		c2_2.addCategory("3月", new Number[] {26, 22});
		c2_2.addCategory("4月", new Number[] {28, 25});
		c2_2.addCategory("5月", new Number[] {26, 22});
		c2_2.addCategory("6月", new Number[] {30, 26});
		vo.addChartData("c2_2", c2_2);

		// 共用c2_2
		DataSource c2_3 = c2_2;
		vo.addChartData("c2_3", c2_3);

		DataSource c2_4 = new DataSource();
		c2_4.setSeriesName(new String[] {"重庆", "北京", "上海"});
		c2_4.addCategory("2021-01", new Number[] {2.0, 1.5, 3.4});
		c2_4.addCategory("2021-02", new Number[] {2.2, 2.0, 2.4});
		c2_4.addCategory("2021-03", new Number[] {2.6, 2.2, 4.3});
		c2_4.addCategory("2021-04", new Number[] {2.8, 2.5, 2.4});
		c2_4.addCategory("2021-05", new Number[] {2.6, 2.2, 3.4});
		c2_4.addCategory("2021-06", new Number[] {3.0, 2.6, 2.7});
		vo.addChartData("c2_4", c2_4);

		DataSource c2_5 = new DataSource();
		c2_5.setSeriesName(new String[] {"男", "女", "平均成本"});
		c2_5.addCategory("2021-01", new Number[] {10, 7, 12300});
		c2_5.addCategory("2021-02", new Number[] {13, 8, 12500});
		c2_5.addCategory("2021-03", new Number[] {14, 8, 12000});
		c2_5.addCategory("2021-04", new Number[] {17, 10, 12300});
		c2_5.addCategory("2021-05", new Number[] {16, 10, 12700});
		c2_5.addCategory("2021-06", new Number[] {18, 12, 13000});
		vo.addChartData("c2_5", c2_5);

		// 共用c2_1
		vo.addChartData("c3_1", c2_1);

		// 共用c2_2
		vo.addChartData("c3_2", c2_2);

		// 共用c2_3
		vo.addChartData("c3_3", c2_3);

		// 共用c2_4
		vo.addChartData("c3_4", c2_4);

		DataSource c3_5 = new DataSource();
		c3_5.setSeriesName(new String[] {"威胁事件"});
		c3_5.addCategory("僵尸网络", new Number[] {10});
		c3_5.addCategory("蠕虫", new Number[] {13});
		c3_5.addCategory("木马", new Number[] {14});
		c3_5.addCategory("勒索软件", new Number[] {17});
		c3_5.addCategory("恶意软件", new Number[] {16});
		c3_5.addCategory("广告软件", new Number[] {12});
		c3_5.addCategory("钓鱼网站", new Number[] {16});
		c3_5.addCategory("矿池", new Number[] {24});
		c3_5.addCategory("APT", new Number[] {7});
		c3_5.addCategory("其他", new Number[] {34});
		vo.addChartData("c3_5", c3_5);


		HashMap<String, String> tableData1 = new HashMap<>();
		tableData1.put("1_1", "30");
		tableData1.put("1_2", "3");
		tableData1.put("1_3", "90.00%");
		tableData1.put("2_1", "20");
		tableData1.put("2_2", "4");
		tableData1.put("2_3", "80.00%");
		tableData1.put("3_1", "10");
		tableData1.put("3_2", "1");
		tableData1.put("3_3", "90.00%");
		tableData1.put("4_1", "50");
		tableData1.put("4_2", "8");
		tableData1.put("4_3", "84.00%");
		vo.addTableReplace("tr1", new TableReplace(){{
			setTable(tableData1);
		}});

		List<String[]> tableData2 = new ArrayList<String[]>();
		tableData2.add(new String[]{"1","渝北区","A34D63A5680BDC01","43","2022-04-30 10:16:06"});
		tableData2.add(new String[]{"2","渝北区","A34D63A5680BDC02","35","2022-04-30 10:17:06"});
		tableData2.add(new String[]{"3","渝北区","A34D63A5680BDC03","33","2022-04-30 10:18:06"});
		tableData2.add(new String[]{"4","渝北区","A34D63A5680BDC04","33","2022-04-30 10:19:06"});
		tableData2.add(new String[]{"5","江北区","A34D63A5680BDC05","27","2022-04-30 10:20:06"});
		tableData2.add(new String[]{"6","江北区","A34D63A5680BDC06","24","2022-04-30 10:21:06"});
		tableData2.add(new String[]{"7","江北区","A34D63A5680BDC07","12","2022-04-30 10:22:06"});
		vo.addTableInsert("ti1", new TableInsert(){{
			setTable(tableData2);
		}});

		return vo;
	}

	@Test
	public void test()  throws Exception{
		String wordTemplate = WordGeneratorTest.class.getClassLoader().getResource("doc-template-test.docx").getPath();
		MyWordGenerator generator = new MyWordGenerator();
		generator.setWordTemplate(wordTemplate); // 设置模板位置
		WordVo vo = getVo(); // 获取报表数据
		String out = "E:\\temp\\poi\\test.docx";
		generator.generator(out, vo);
		File fo = new File(out);
		Assertions.assertTrue(fo.exists());
	}

}

class MyWordGenerator extends WordGenerator{
	@Override
	protected void handle(XWPFDocument document, WordVo vo) throws Exception {
		//替换文本段落占位符
		WordUtil.replaceText(document, vo.getTextReplaces());

		//替换图表
		WordUtil.replaceChart(document, ChartTypes.PIE, "C2.1", vo.getChartDatas("c2_1"));
		WordUtil.replaceChart(document, ChartTypes.BAR, "C2.2", vo.getChartDatas("c2_2"));
		WordUtil.replaceChart(document, ChartTypes.BAR, "C2.3", vo.getChartDatas("c2_3"));
		WordUtil.replaceChart(document, ChartTypes.LINE, "C2.4", vo.getChartDatas("c2_4"));
		WordUtil.replaceChart(document, new ChartTypes[]{ChartTypes.BAR, ChartTypes.LINE}, "C2.5", vo.getChartDatas("c2_5"));

		//插入图表
		WordUtil.insertChart(document, "#chart1#", ChartTypes.PIE, vo.getChartDatas("c3_1"), new ChartParam(){{
			setPosition(LegendPosition.RIGHT);
			setTitle("盈利额");
			setDlPos(STDLblPos.OUT_END);
			setDlShowCatName(true);
			setDlShowPercent(true);
		}});
		WordUtil.insertChart(document, "#chart2#", ChartTypes.BAR, vo.getChartDatas("c3_2"), new ChartParam(){{
			setPosition(LegendPosition.TOP);
			setTitle("人数分布");
		}});
		WordUtil.insertChart(document, "#chart3#", ChartTypes.BAR, vo.getChartDatas("c3_3"), new ChartParam(){{
			setPosition(LegendPosition.TOP);
			setTitle("人数分布");
			setBarGrouping(BarGrouping.STACKED);
		}});
		WordUtil.insertChart(document, "#chart4#", ChartTypes.LINE, vo.getChartDatas("c3_4"), new ChartParam(){{
			setTitle("城市降雨量");
			setXTitle("月份");
			setYTitle("月累计（毫米）");
			setLineMarkerStyle(new MarkerStyle[] {MarkerStyle.CIRCLE, MarkerStyle.SQUARE, MarkerStyle.DIAMOND});
		}});
		WordUtil.insertChart(document, "#chart5#", ChartTypes.LINE, vo.getChartDatas("c3_5"), null);

		//替换表格占位符
		WordUtil.replaceTable(document, 0, vo.getTableReplaces("tr1"));

		//向表格添加数据
		WordUtil.insertTable(document, 1, 1, vo.getTableInserts("ti1"));
	}
}

