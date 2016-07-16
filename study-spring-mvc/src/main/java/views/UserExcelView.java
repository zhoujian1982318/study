package views;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class UserExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			//String excelName = "user.xls";  
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
			//response.setContentType("APPLICATION/OCTET-STREAM");  
			//response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(excelName, "UTF-8"));
			 List<String> list = (List<String>) model.get("list");
			 // 产生Excel表头  
		     HSSFSheet sheet = workbook.createSheet("list");  
		     HSSFRow header = sheet.createRow(0); // 第0行  
		     header.createCell(0).setCellValue("title"); 
		     int rowNum = 1;  
		     for (Iterator<String> iter = list.iterator(); iter.hasNext();) {  
		            String element = (String) iter.next();  
		            HSSFRow row = sheet.createRow(rowNum++);  
		            row.createCell(0).setCellValue(element);  
		     }  
		
	}
}
