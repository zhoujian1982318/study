package com.github.report.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.report.demo.model.Account;
import com.github.report.demo.model.Alert;

public class DynamicLineChart {
	private static Logger logger = LoggerFactory.getLogger(DynamicLineChart.class);
	private static String template = "dynamic_line_chart_template.xlsx";
    private static String output = "target/dynamic_line_chart_output.xlsx";
    
	public static void main(String[] args) throws IOException {
		 	List<Account> accounts = createAccounts();
	        logger.info("Running dynamic  line chart demo");
	        try (InputStream is = DynamicLineChart.class.getResourceAsStream(template)) {
	            try (OutputStream os = new FileOutputStream(output)) {
	                Context context = PoiTransformer.createInitialContext();
	                context.putVar("accounts", accounts);
	                context.putVar("sheetNames", Arrays.asList(
	                		accounts.get(0).getName(),
	                		accounts.get(1).getName()));
	                // with multi sheets it is better to use StandardFormulaProcessor by disabling the FastFormulaProcessor
	                JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(is, os, context);
	                
	                InputStream sourceBytes = new FileInputStream(output);
	                XSSFWorkbook workbook = new XSSFWorkbook(sourceBytes);
	                XSSFName name =workbook.getName("pieval");
	                name.setSheetIndex(-1);
	                name.setRefersToFormula("Template!A1:A1");
	                
	                Sheet sheet = workbook.getSheet("Template");
	                Drawing drawing = sheet.createDrawingPatriarch();
	                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 1, 10, 10);
	                Chart chart = drawing.createChart(anchor);
	                ChartLegend legend = chart.getOrCreateLegend();
	                legend.setPosition(LegendPosition.TOP_RIGHT);
	                
	                LineChartData data = chart.getChartDataFactory().createLineChartData();
	                
	                // Use a category axis for the bottom axis.
	                ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
	                ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
	                leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
	                Sheet account1Sheet = workbook.getSheet(accounts.get(0).getName());
	                ChartDataSource<Number> xs = DataSources.fromNumericCellRange(account1Sheet, new CellRangeAddress(3, 3, 0, 4));
	                ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(account1Sheet, new CellRangeAddress(4, 4, 0, 4));
	              
	                data.addSeries(xs, ys1);
	                chart.plot(data, bottomAxis, leftAxis);
	                
	                ClientAnchor anchor1 = drawing.createAnchor(0, 0, 0, 0, 0, 11, 10, 21);

	                Chart chart1 = drawing.createChart(anchor1);
	                ChartAxis bottomAxis1 = chart1.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
	                ValueAxis leftAxis1 = chart1.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
	                leftAxis1.setCrosses(AxisCrosses.AUTO_ZERO);
	                LineChartData data1 = chart1.getChartDataFactory().createLineChartData();
	                
	     
	               
	                Sheet account2Sheet = workbook.getSheet(accounts.get(1).getName());
	                xs = DataSources.fromNumericCellRange(account2Sheet, new CellRangeAddress(3, 3, 0, 4));
	                ys1 = DataSources.fromNumericCellRange(account2Sheet, new CellRangeAddress(4, 4, 0, 4));
	                data1.addSeries(xs, ys1);
	                chart1.plot(data1, bottomAxis1, leftAxis1);
	                
	                

	                // Write the output to a file
	                FileOutputStream fileOut = new FileOutputStream("target/dynamic_line_chart_output2.xlsx");
	                workbook.write(fileOut);
	                workbook.close();
	                fileOut.close();
	                
	            }
	        }
	}

	private static List<Account> createAccounts() {
		 	List<Account> accounts = new ArrayList<Account>();
		 	Account account = new Account("洛阳农信",12);
		 	Alert alert = new Alert(10,15,30,20,15);
		 	account.getAlerts().add(alert);
		 	accounts.add(account);
		 	Account account1 = new Account("交行百场",50);
		 	Alert alert1 = new Alert(2,4,6,8,2);
		 	account1.getAlerts().add(alert1);
		 	accounts.add(account1);
	        return accounts;
	}
}
