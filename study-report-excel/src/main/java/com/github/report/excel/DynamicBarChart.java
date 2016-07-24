package com.github.report.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFChart;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DynamicBarChart {
	private static String template = "grid_output2.xls";
	public static void main(String[] args) throws IOException {
		 
			InputStream is = DynamicBarChart.class.getResourceAsStream(template);
		    try {
		    	HSSFWorkbook workbook = new HSSFWorkbook(is);
		    	HSSFSheet sheet = workbook.getSheet("Sheet2");
		    	HSSFChart[] charts = HSSFChart.getSheetCharts(sheet);
		    	HSSFChart barChart = charts[0];
//		    	HSSFSeries[] series = barChart.getSeries();
//		    	for (HSSFSeries hssfSeries : series) {
//		    		barChart.removeSeries(hssfSeries);
//				}
//		    	int chartX = barChart.getChartX();
//		    	int chartY = barChart.getChartY();
//		    	int chartH = barChart.getChartHeight();
//		    	int chartW = barChart.getChartWidth();
//		    	System.out.println("chartX="+chartX);
//		    	System.out.println("chartY="+chartY);
//		    	System.out.println("chartH="+chartH);
//		    	System.out.println("chartW="+chartW);
		    	
		    	HSSFPatriarch prch =sheet.getDrawingPatriarch();
		    	List<HSSFShape> shapes = prch.getChildren();
		    	prch.removeShape(shapes.get(0));
//		    	for (HSSFShape hssfShape : shapes) {
//		    		prch.removeShape(hssfShape);
//				}
		    	
		    	FileOutputStream fileOut = new FileOutputStream("target/grid_output2_out.xls");
	            workbook.write(fileOut);
	            workbook.close();
	            fileOut.close();
		    } finally {
		    	is.close();
		    }
	}
}
