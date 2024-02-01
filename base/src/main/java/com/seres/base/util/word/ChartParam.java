package com.seres.base.util.word;

import lombok.Data;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.BarGrouping;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.openxmlformats.schemas.drawingml.x2006.chart.STDLblPos;

@Data
public class ChartParam {
	/**
	 * 图例位置，默认为：LegendPosition.TOP
	 */
	private LegendPosition position = LegendPosition.TOP;

	/**
	 * 图表标题，为 null 则不显示
	 */
	private String title;
	/**
	 * x坐标标题，为 null 则不显示
	 */
	private String xTitle;
	/**
	 * x坐标标题，为 null 则不显示
	 */
	private String yTitle;

	/**
	 * 数据标签-是否显示图例标识
	 */
	private boolean dlShowLegendKey = false;
	/**
	 * 数据标签-是否显示类目名
	 */
	private boolean dlShowCatName = false;
	/**
	 * 数据标签-是否显示系列名
	 */
	private boolean dlShowSerName = false;
	/**
	 * 数据标签-是否显示值
	 */
	private boolean dlShowVal = true;
	/**
	 * 数据标签-是否显示百分比
	 */
	private boolean dlShowPercent = false;
	/**
	 * 数据标签-分隔符
	 */
	private String dlSeparator = "\n";
	/**
	 * 数据标签-显示位置，默认图内
	 */
	private STDLblPos.Enum dlPos = STDLblPos.IN_END;

	/**
	 * 柱状图方向，默认竖向
	 */
	private BarDirection barDirection = BarDirection.COL;
	/**
	 * 柱状图分组，默认标准，其他可选堆叠等
	 */
	private BarGrouping barGrouping = BarGrouping.STANDARD;

	/**
	 * 折线图是否平滑
	 */
	private boolean lineSmooth = false;
	/**
	 * 折线图样式，与系列一一对应，推荐使用默认的随机，使用枚举，如：MarkerStyle.CIRCLE, MarkerStyle.SQUARE
	 */
	private MarkerStyle[] lineMarkerStyle;
}
