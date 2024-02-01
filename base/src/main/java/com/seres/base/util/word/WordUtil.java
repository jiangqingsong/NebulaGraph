package com.seres.base.util.word;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author ：liyu
 * @date ：2022/10/8 13:14
 */
@Slf4j
public class WordUtil {

	private static final String SHEET_NAME = "Sheet1";

	/**
	 * 替换段落文本中的占位符，#[xxx]，如：模板中的 #[create_time]
	 * @param document docx解析对象
	 * @param textMap 需要替换的数据映射
	 */
	public static void replaceText(XWPFDocument document, Map<String, String> textMap){
		//获取段落集合
		List<XWPFParagraph> paragraphs = document.getParagraphs();

		for (XWPFParagraph paragraph : paragraphs) {
			//判断此段落时候需要进行替换
			String text = paragraph.getText();
			if(checkText(text)){
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					//替换模板原来位置
					if(checkText(run.toString())){
						run.setText(changeValue(run.toString(), textMap),0);
					}
				}
			}
		}
	}


	/**
	 * 按图表模板标记替换图表，效率不及按下标替换
	 * @param document
	 * @param type 图表类型，如：ChartTypes.LINE
	 * @param templateKey 图表模板标记，内置excel中的第一格内容
	 * @param dataSource
	 * @throws Exception
	 */
	public static void replaceChart(XWPFDocument document, ChartTypes type, String templateKey, DataSource dataSource) throws Exception {
		XWPFChart chart = getChartTemplate(document, templateKey);
		replaceChart(chart, type, dataSource);
	}

	/**
	 * 按图表模板标记替换图表，效率不及按下标替换
	 * @param document
	 * @param types 图表类型，如：ChartTypes.BAR, ChartTypes.LINE，多个按模板内置excel表格系列顺序传入
	 * @param templateKey 图表模板标记，内置excel中的第一格内容
	 * @param dataSource
	 * @throws Exception
	 */
	public static void replaceChart(XWPFDocument document, ChartTypes[] types, String templateKey, DataSource dataSource) throws Exception {
		XWPFChart chart = getChartTemplate(document, templateKey);
		replaceChart(chart, types, dataSource);
	}

	/**
	 * 按图表下标替换图表，该方法在改变图表模板时（新增、删除），序号会发生变化，慎用或加大测试力度
	 * @param document
	 * @param type 图表类型，如：ChartTypes.LINE
	 * @param chartIndex 图表创建时的序号（非前后顺序），下标从0开始
	 * @param dataSource
	 * @throws Exception
	 */
	public static void replaceChart(XWPFDocument document, ChartTypes type, int chartIndex, DataSource dataSource) throws Exception {
		XWPFChart chart = getChartTemplate(document, chartIndex);
		replaceChart(chart, type, dataSource);
	}

	/**
	 * 按图表下标替换图表，该方法在改变图表模板时（新增、删除），序号会发生变化，慎用或加大测试力度
	 * @param document
	 * @param types 图表类型，如：ChartTypes.BAR, ChartTypes.LINE，多个按模板内置excel表格系列顺序传入
	 * @param chartIndex 图表创建时的序号（非前后顺序），下标从0开始
	 * @param dataSource
	 * @throws Exception
	 */
	public static void replaceChart(XWPFDocument document, ChartTypes[] types, int chartIndex, DataSource dataSource) throws Exception {
		XWPFChart chart = getChartTemplate(document, chartIndex);
		replaceChart(chart, types, dataSource);
	}

	/**
	 * 指定位置插入图表（饼图、折线图、柱状图测试过，其他待补充/测试），不支持组合图
	 * @param document 模板对象
	 * @param chartTag 图表位置占位符，如：#chart1#
	 * @param type 图表类型，如：ChartTypes.LINE
	 * @param dataSource 图表数据
	 * @param param 图表参数
	 * @throws Exception
	 */
	public static void insertChart(XWPFDocument document, String chartTag, ChartTypes type, DataSource dataSource, ChartParam param) throws Exception{
		if(Objects.isNull(param)){
			param = new ChartParam();
		}

		List<XWPFParagraph> paragraphs = document.getParagraphs();
		for (XWPFParagraph paragraph : paragraphs) {
			//判断此段落时候需要进行替换
			String text = paragraph.getText();
			if(!text.contains(chartTag)){
				continue;
			}
			List<XWPFRun> runs = paragraph.getRuns();
			if(null == runs || runs.isEmpty()){
				continue;
			}
			// 1、图表数据
			String[] seriesNames = dataSource.getSeriesName();
			LinkedHashMap<String, Number[]> category = dataSource.getCategory();
			if(null == category || category.isEmpty()){
				runs.get(0).setText("暂无数据", 0);
				return;
			}
			runs.get(0).setText("", 0);

			// 2、创建chart图表对象，抛出异常
			XWPFChart chart = document.createChart(runs.get(0),15 * Units.EMU_PER_CENTIMETER, 12 * Units.EMU_PER_CENTIMETER);

			// 3、图表标题设置
			if(null == param.getTitle()) {
				chart.setTitleText(null); // 不显示标题
			}else{
				chart.setTitleText(param.getTitle()); // 图表标题
			}
			chart.setTitleOverlay(false); // 图例是否覆盖标题

			// 4、图例设置
			XDDFChartLegend legend = chart.getOrAddLegend();
			legend.setPosition(param.getPosition()); // 图例位置:上下左右

			// 5、X轴(分类轴)相关设置
			XDDFCategoryAxis xAxis = null;
			if(ChartTypes.PIE != type){ // 饼图不需要坐标轴
				xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM); // 创建X轴,并且指定位置
				if(null != param.getXTitle()){
					xAxis.setTitle(param.getXTitle()); // x轴标题
				}
			}

			// 6、Y轴(值轴)相关设置
			XDDFValueAxis yAxis = null;
			if(ChartTypes.PIE != type){ // 饼图不需要坐标轴
				yAxis = chart.createValueAxis(AxisPosition.LEFT); // 创建Y轴,指定位置
				if(null != param.getYTitle()){
					yAxis.setTitle(param.getYTitle()); // Y轴标题
				}
				yAxis.setCrossBetween(AxisCrossBetween.BETWEEN); // 设置位置:BETWEEN居中
			}

			// 7、创建图表数据对象
			XDDFChartData chartData = chart.createData(type, xAxis, yAxis);
			chartData.setVaryColors(true); // 自动可变彩色
			// 个性化设置
			switch (type) {
				case BAR:
					((XDDFBarChartData)chartData).setBarDirection(param.getBarDirection()); // 设置柱状图的方向：BAR横向，COL竖向，默认是BAR
					((XDDFBarChartData)chartData).setBarGrouping(param.getBarGrouping()); // 设置分组形式，如：堆叠 BarGrouping.STACKED
					break;
				case PIE:

					break;
				case LINE:

					break;
				default:
					break;
			}

			// 8、加载图表数据集
			XDDFCategoryDataSource xAxisSource = XDDFDataSourcesFactory.fromArray(category.keySet().toArray(new String[category.size()])); // 设置X轴数据
			for (int i=0; i<seriesNames.length; i++){
				List<Number> yAxisDatas = new ArrayList<>();
				int index = i;
				category.values().forEach(vs->{
					yAxisDatas.add(vs[index]);
				});
				XDDFNumericalDataSource<Number> yAxisSource = XDDFDataSourcesFactory.fromArray(yAxisDatas.toArray(new Number[category.size()])); // 设置Y轴数据
				XDDFChartData.Series series = chartData.addSeries(xAxisSource, yAxisSource);
				series.setTitle(seriesNames[i], null); // 图例标题
				// 个性化设置
				switch (type) {
					case LINE:
						((XDDFLineChartData.Series)series).setMarkerSize((short) 6); // 标记点大小
						((XDDFLineChartData.Series)series).setSmooth(param.isLineSmooth()); // 线条样式：true平滑曲线，false折线
						if(null != param.getLineMarkerStyle()){
							((XDDFLineChartData.Series)series).setMarkerStyle(param.getLineMarkerStyle()[i]); // 标记点样式
						}
						break;
					case PIE:

						break;
					case BAR:

						break;
					default:
						break;
				}
				// series.setFillProperties(new XDDFSolidFillProperties(XDDFColor.from(PresetColor.BLUE_VIOLET))); // 填充色，默认自动彩色
			}

			// 9、绘制图
			chart.plot(chartData);
			// 10、设置图表数据标签
			for (int i=0; i<seriesNames.length; i++){
				// 个性化获取
				CTDLbls cts = null;
				switch (type) {
					case PIE:
						cts = chart.getCTChart().getPlotArea().getPieChartArray(0).getSerArray(i).addNewDLbls();
						// 个性化设置
						cts.addNewShowPercent().setVal(param.isDlShowPercent()); // 百分比
						cts.addNewDLblPos().setVal(param.getDlPos()); //数据标签位置
						break;
					case BAR:
						cts = chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(i).addNewDLbls();
						break;
					case LINE:
						cts = chart.getCTChart().getPlotArea().getLineChartArray(0).getSerArray(i).addNewDLbls();
						break;
					default:
						break;
				}
				// 通用设置
				if(!Objects.isNull(cts)) {
					//引导线
					cts.addNewShowLeaderLines().setVal(true);

					//图例项标识
					cts.addNewShowLegendKey().setVal(param.isDlShowLegendKey());
					//类别名称
					cts.addNewShowCatName().setVal(param.isDlShowCatName());
					//系列名称
					cts.addNewShowSerName().setVal(param.isDlShowSerName());
					//值
					cts.addNewShowVal().setVal(param.isDlShowVal());
					//分隔符为分行符
					cts.setSeparator(param.getDlSeparator());
				}
			}
		}
	}


	/**
	 * 替换表格占位符 #[xxx]
	 * @param document
	 * @param tableIndex 第几个表格，下标从0开始
	 * @param tableReplace 需要替换的数据映射
	 */
	public static void replaceTable(XWPFDocument document, int tableIndex, TableReplace tableReplace){
		Map<String, String> tableData = tableReplace.getTable();
		if(null == tableData || tableData.isEmpty()){
			return;
		}
		XWPFTable table = document.getTableArray(tableIndex);
		List<XWPFTableRow> rows = table.getRows();
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				//判断单元格是否需要替换
				String text = cell.getText();
				if(checkText(text)){
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for (XWPFParagraph paragraph : paragraphs) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun run : runs) {
							run.setText(changeValue(run.toString(), tableData),0);
						}
					}
				}
			}
		}
	}

	/**
	 * 为表格插入数据，模板中除了标题有一行空的数据，行数不够自动添加新行
	 * @param document
	 * @param tableIndex 第几个表格，下标从0开始
	 * @param startRow  数据开始行数（表头占用行数），下标从0开始计算
	 * @param tableInsert 需要添加的数据集合
	 */
	public static void insertTable(XWPFDocument document, int tableIndex, int startRow, TableInsert tableInsert){
		List<String[]> tableData = tableInsert.getTable();
		if(null == tableData || tableData.isEmpty()){
			return;
		}

		XWPFTable table = document.getTableArray(tableIndex);
		//创建行，根据需要插入的数据添加新行，不处理表头，模板里面默认有一行了
		for(int i = 1; i < tableData.size(); i++){
			table.createRow();
		}

		List<XWPFTableRow> rows = table.getRows();
		//遍历数据插入表格
		for(int j = 0; j < tableData.size(); j++){
			String[] data = tableData.get(j);
			XWPFTableRow row = rows.get(j + startRow);
			for(int k = 0; k < data.length; k++){
				row.getCell(k).setText(data[k]);
			}
		}
	}

	/////////////////////////////////////

	/**
	 * 判断文本中是否包含#[和]
	 * @param template 文本
	 * @return 包含返回true,不包含返回false
	 */
	private static boolean checkText(String template){
		return (template.contains("#[") && template.contains("]"));
	}

	/**
	 * 匹配传入信息集合与模板
	 * @param template 模板需要替换的区域
	 * @param textMap 传入信息集合
	 * @return 模板需要替换区域信息集合对应值
	 */
	private static String changeValue(String template, Map<String, String> textMap){
		Set<Map.Entry<String, String>> textSets = textMap.entrySet();
		for (Map.Entry<String, String> textSet : textSets) {
			//匹配模板与替换值 格式#[key]
			String key = "#["+textSet.getKey()+"]";
			if(template.indexOf(key)!= -1){
				template = template.replace(key, textSet.getValue());
			}
		}
		//模板未匹配到区域替换为0
		if(checkText(template)){
			template = "0";
		}
		return template;
	}

	/**
	 * 替换图表（单图表）
	 * @param chart 图表对象
	 * @param type 图表类型，如：ChartTypes.LINE
	 * @param dataSource 图表数据
	 * @throws Exception
	 */
	private static void replaceChart(XWPFChart chart, ChartTypes type, DataSource dataSource) throws Exception {
		if(Objects.isNull(chart)){
			throw new Exception("模板图表未找到！");
		}
		//刷新内置excel数据
		refreshExcel(chart, dataSource);
		//刷新图表显示数据
		refreshGraph(chart, new ChartTypes[]{type}, dataSource);
	}

	/**
	 * 替换图表（组合图表）
	 * @param chart 图表对象
	 * @param types 图表类型，如：ChartTypes.BAR, ChartTypes.LINE，多个按模板内置excel表格系列顺序传入
	 * @param dataSource 图表数据
	 * @throws Exception
	 */
	private static void replaceChart(XWPFChart chart, ChartTypes[] types, DataSource dataSource) throws Exception {
		if(Objects.isNull(chart)){
			throw new Exception("模板图表未找到！");
		}
		//刷新内置excel数据
		refreshExcel(chart, dataSource);
		//刷新图表显示数据
		refreshGraph(chart, types, dataSource);
	}

	/**
	 * 刷新内置excel数据
	 * @param chart
	 * @param dataSource
	 * @throws IOException
	 */
	private static void refreshExcel(XWPFChart chart, DataSource dataSource) throws IOException {
		// 更新嵌入的workbook
		POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
		try (Workbook wb = new XSSFWorkbook();
			 OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream()){
			Sheet sheet = wb.createSheet(SHEET_NAME);

			int row = 0;
			//根据数据创建excel第一行标题行
			String[] series = dataSource.getSeriesName();
			sheet.createRow(row).createCell(0).setCellValue(" ");   // 1,1
			for (int i = 0; i < series.length; i++) {
				sheet.getRow(row).createCell(i+1).setCellValue(series[i] == null ? "" : series[i]);
			}

			//遍历数据行
			LinkedHashMap<String, Number[]> category = dataSource.getCategory();
			for(String name : category.keySet()) {
				Number[] values = category.get(name);
				if(null == name || null == values || values.length == 0){
					continue;
				}
				row ++;
				sheet.createRow(row).createCell(0).setCellValue(name);
				for (int i = 0; i < values.length; i++) {
					sheet.getRow(row).createCell(i+1).setCellValue(values[i].doubleValue());
				}
			}
			wb.write(xlsOut);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 获取图表模板
	 * @param document
	 * @param chartIndex 获取哪一个图表，从0开始
	 * @throws Exception
	 */
	private static XWPFChart getChartTemplate(XWPFDocument document, int chartIndex) {
		List<XWPFChart> charts = document.getCharts();
		List<XWPFChart> cts = new ArrayList();
		cts.addAll(charts);
		Collections.sort(cts, Comparator.comparing(o -> o.getPackagePart().getPartName().getName()));
		return cts.get(chartIndex);
	}

	/**
	 * 获取图表模板，根据模板内置excel中的第一格中设置的内容作为模板标记（不要重复）
	 * @param document
	 * @param templateKey 图表模板标记，内置excel中的第一格内容
	 * @throws IOException
	 */
	private static XWPFChart getChartTemplate(XWPFDocument document, String templateKey) throws IOException {
		List<XWPFChart> charts = document.getCharts();
		for(XWPFChart chart : charts){
			POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
			try (InputStream xlsIn = xlsPart.getPackagePart().getInputStream()){
				Workbook wb = new XSSFWorkbook(xlsIn);
				Sheet sheet = wb.getSheetAt(0);
				String flag = sheet.getRow(0).getCell(0).getStringCellValue();
				if(null != flag){
					flag = flag.trim();
				}
				if(templateKey.equals(flag)){
					return chart;
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return null;
	}

	/**
	 * 刷新图表显示
	 * @param chart
	 * @param types 图表类型，如：ChartTypes.BAR, ChartTypes.LINE，多个按模板内置excel表格系列顺序传入
	 * @param dataSource 映射数据
	 * @throws Exception
	 */
	private static void refreshGraph(XWPFChart chart, ChartTypes[] types, DataSource dataSource) throws Exception{
		String[] serNames = dataSource.getSeriesName();
		LinkedHashMap<String, Number[]> catData = dataSource.getCategory();
		Set<String> catNames = catData.keySet();

		CTPlotArea plotArea = chart.getCTChart().getPlotArea();
		List<Object> allSer = new ArrayList<>();

		for(ChartTypes type : types) {
			switch (type) {
				case PIE:
					allSer.addAll(plotArea.getPieChartArray(0).getSerList());
					break;
				case BAR:
					allSer.addAll(plotArea.getBarChartArray(0).getSerList());
					break;
				case LINE:
					allSer.addAll(plotArea.getLineChartArray(0).getSerList());
					break;
				default:
					log.error("存在不能处理的图表类型：{}，请补充上面switch-case及下面instanceof中相应类型的图表获取方法", type);
					throw new Exception("存在不能处理的图表类型：" + type);
			}
		}

		if(serNames.length != allSer.size()){
			log.error("模板中的系列维度与数据中的系列维度不一致");
			throw new Exception("模板中的系列维度与数据中的系列维度不一致");
		}

		for(int i=0; i<serNames.length; i++) {
			// 某一系列横坐标，类目
			CTAxDataSource cat = null;
			// 某一系列纵坐标，值
			CTNumDataSource val = null;
			// 某一系列标题（图例）
			CTSerTx tx = null;

			Object ser = allSer.get(i);
			if(ser instanceof CTPieSer){
				CTPieSer currSer = (CTPieSer) ser;
				tx = currSer.getTx();
				cat = currSer.getCat();
				val = currSer.getVal();
			} else if(ser instanceof CTBarSer){
				CTBarSer currSer = (CTBarSer) ser;
				tx = currSer.getTx();
				cat = currSer.getCat();
				val = currSer.getVal();
			} else if(ser instanceof CTLineSer){
				CTLineSer currSer = (CTLineSer) ser;
				tx = currSer.getTx();
				cat = currSer.getCat();
				val = currSer.getVal();
			}else{
				log.error("存在不能处理的图表类型：{}，请补充上面switch-case及下面instanceof中相应类型的图表获取方法", ser.getClass().getName());
				throw new Exception("存在不能处理的图表类型：" + ser.getClass().getName());
			}

			CTStrData strData = cat.getStrRef().getStrCache();
			CTNumData numData = val.getNumRef().getNumCache();
			strData.setPtArray((CTStrVal[]) null); // 重置为空
			numData.setPtArray((CTNumVal[]) null); // 重置为空

			// 重新赋值
			tx.getStrRef().getStrCache().getPtArray(0).setV(serNames[i]); // 系列标题（图例）
			long idx = 0; // 当前系列的当前处理下标
			for(String catName: catNames){  // 遍历处理某一列（系列）的每一行（类目），对应excel的2、3、4...行），即数据行
				//序列名称
				CTStrVal strVal = strData.addNewPt();
				strVal.setIdx(idx);
				strVal.setV(catName);
				//序列值
				CTNumVal numVal = numData.addNewPt();
				Number[] data = catData.get(catName);
				numVal.setIdx(idx);
				numVal.setV(data[i].toString());
				idx++;
			}
			numData.getPtCount().setVal(idx);  // 长度
			strData.getPtCount().setVal(idx);  // 长度

			//数据区域-横坐标
			String axisDataRange = new CellRangeAddress(1, catData.size(), 0, 0)
					.formatAsString(SHEET_NAME, true);
			cat.getStrRef().setF(axisDataRange);
			//数据区域-数值
			String numDataRange = new CellRangeAddress(1, catData.size(), i + 1, i + 1)
					.formatAsString(SHEET_NAME, true);
			val.getNumRef().setF(numDataRange);
			// 图例区域
			String legendRange = new CellRangeAddress(0, 0, i + 1, i + 1)
					.formatAsString(SHEET_NAME, true);
			tx.getStrRef().setF(legendRange);
		}
	}

	/**
	 * 刷新图表，wps正常，office存在问题
	 * @param chart 图表对象
	 * @param dataSource 数据源
	 * @throws Exception
	 */
	@Deprecated
	private static void refreshGraph(XWPFChart chart, DataSource dataSource) throws Exception {
		// 数据表
		XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
		List<XDDFChartData> css = chart.getChartSeries();
		String[] sns = dataSource.getSeriesName(); // 系列名称
		int serNum = sns.length;  // 表格中系列数
		int categoryNum = dataSource.getCategory().size();  // 数据行数
		int index = 0;
		for(XDDFChartData chartData: css){
			int tempSc = chartData.getSeriesCount(); // 模板中系列数
			if(tempSc == 0){
				continue;
			}
			for(int i=0; i<tempSc; i++){
				index++;
				XDDFCategoryDataSource cat = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, categoryNum, 0, 0));
				XDDFNumericalDataSource<Double> val = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, categoryNum, index, index));
				// 替换原有数据
				XDDFChartData.Series ser = chartData.getSeries(i);
				ser.replaceData(cat, val);
				ser.setTitle(sns[index-1], null);
			}
			// 重新绘图
			chart.plot(chartData);
		}
		if(serNum > index){
			log.warn("数据系列数超过模板中的系列总数{}，【{}】及之后的数据将被忽略", index, sns[index]);
		}
	}

}



