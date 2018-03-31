package com.github.report.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFAnchor;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFConnector;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFGraphicFrame;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFShapeGroup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFManualLayout;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTConnector;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTDrawing;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTGraphicalObjectFrame;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTGroupShape;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTOneCellAnchor;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTShape;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTTwoCellAnchor;

public class DynamicBarChart {
	private static String template = "test_chart_2.xlsx";
	public static void main(String[] args) throws IOException {
		 
			InputStream is = DynamicBarChart.class.getResourceAsStream(template);
		    try {
		    	
                XSSFWorkbook workbook = new XSSFWorkbook(is);
                XSSFSheet sheet = workbook.getSheet("Template");
                XSSFDrawing  drawing = sheet.getDrawingPatriarch();
                List<XSSFChart> charts = sheet.getDrawingPatriarch().getCharts();
         
                XSSFChart test = charts.get(0);
                System.out.println(test.getCTChart());
                System.out.println(test.getGraphicFrame());
              
                //CTChartSpace cs = test.getCTChartSpace();
                XSSFManualLayout layout = test.getManualLayout();
    //            layout.setX(0);
    //            layout.setY(0);
                //layout.
                System.out.println(layout.getCTManualLayout());
                //test.getGraphicFrame();
                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 25, 10, 35);
                
                XSSFChart chart = drawing.createChart(anchor);
                
                ChartAxis bottomAxis1 = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
                ValueAxis leftAxis1 = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
                leftAxis1.setCrosses(AxisCrosses.AUTO_ZERO);
                LineChartData data1 = chart.getChartDataFactory().createLineChartData();
                
     
               
                Sheet account2Sheet = workbook.getSheetAt(1);
                ChartDataSource<Number> xs = DataSources.fromNumericCellRange(account2Sheet, new CellRangeAddress(3, 3, 0, 4));
                ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(account2Sheet, new CellRangeAddress(4, 4, 0, 4));
                data1.addSeries(xs, ys1);
                chart.plot(data1, bottomAxis1, leftAxis1);
                
                System.out.println(chart.getGraphicFrame());
                
                short col1 = chart.getGraphicFrame().getAnchor().getCol1();
                int row1 = chart.getGraphicFrame().getAnchor().getRow1();
                
                //chart.getGraphicFrame().getAnchor().setRow1(25);
              
                chart.getGraphicFrame().getAnchor().setRow1(30);
                chart.getGraphicFrame().getAnchor().setRow2(40);
                System.out.println(chart.getGraphicFrame().getAnchor().getFrom());
                System.out.println(chart.getGraphicFrame().getAnchor().getTo());
                System.out.println(col1);
                System.out.println(row1);
                //sheet.getDrawingPatriarch().createChart(anchor)
               
       //         System.out.println(test.getGraphicFrame().getAnchor().getCol1());
//		    	HSSFWorkbook workbook = new HSSFWorkbook(is);
//		    	HSSFSheet sheet = workbook.getSheet("Chart");
//		    ;
//		    	HSSFChart[] charts = HSSFChart.getSheetCharts(sheet);
//		    	System.out.println(charts.length);
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
		    	
//		    	EscherAggregate aggregate = sheet.getDrawingEscherAggregate();
//		    	aggregate.clearEscherRecords();
//		    	
//		    	HSSFPatriarch prch =sheet.getDrawingPatriarch();
//		    	
//		    	HSSFClientAnchor anchor = prch.createAnchor(0, 0, 0, 0, 0, 1, 7, 17);
//		    	
//		    	prch.createTextbox(anchor);
		    	//prch.
//		    	Iterator<HSSFShape> iter  = prch.iterator();
//		    	while(iter.hasNext()){
//		    		HSSFShape shape = iter.next();
//		    	}
//		    	List<HSSFShape> shapes = prch.getChildren();
//		    	System.out.println(shapes.size());
//		    	
//		    	prch.removeShape(shapes.get(0));
//		    	for (HSSFShape hssfShape : shapes) {
//		    		prch.removeShape(hssfShape);	
//				}
//		    	HSSFChart test = charts[0];
//		    	test.setChartX(5);
//		    	test.setChartY(5);
//		    	test.setChartHeight(100);
//		    	test.setChartWidth(100);
//		    	
//		    
//		    	//charts[0].setChartWidth(-1);
//		    	System.out.println(test.getChartX());
//		    	System.out.println(test.getChartY());
//		    	System.out.println(test.getChartHeight());
//		    	System.out.println(test.getChartWidth());
//		    	System.out.println(test.getChartTitle());
                List<XSSFShape> shapes = drawing.getShapes();
                XSSFShape shape = shapes.get(0);
                System.out.println(shape);
                System.out.println(shape.getAnchor());
                XSSFGraphicFrame frame = (XSSFGraphicFrame) shape;
//                List<XSSFShape> lst = new ArrayList<XSSFShape>();
//               CTDrawing  ctDw = drawing.getCTDrawing();
//                for(XmlObject obj : ctDw.selectPath("./*/*")) {
//                    .anchor = getAnchorFromParent(obj);
//                     
//                }
                System.out.println(frame.getAnchor());
//                shapes.get(0).getAnchor().setDy1(0);
//                shapes.get(0).getAnchor().setDx2(0);
//                shapes.get(0).getAnchor().setDy2(0);
		    	FileOutputStream fileOut = new FileOutputStream("target/grid_output2_out.xlsx");
	            workbook.write(fileOut);
	            workbook.close();
	            fileOut.close();
		    } finally {
		    	is.close();
		    }
	}
	
	 
}
