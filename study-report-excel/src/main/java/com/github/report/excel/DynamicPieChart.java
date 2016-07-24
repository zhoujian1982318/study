/**
 * 
 */
package com.github.report.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.command.GridCommand;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.template.SimpleExporter;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.report.demo.model.Account;

/**
 * @author Administrator
 *
 */
public class DynamicPieChart {
	private static String template = "dynamic_pie_chart_template.xlsx";
	
	private static Logger logger = LoggerFactory.getLogger(DynamicPieChart.class);
	
	public static void main(String[] args) throws IOException {
		logger.info("Running dynamic  pie chart demo");
		List<Account> accounts = generateSampleAccountData();
		List<String> headers = Arrays.asList("Accout Name","AP Numbers");
		try (InputStream is = DynamicPieChart.class.getResourceAsStream(template)) {
			try (OutputStream os2 = new FileOutputStream("target/dynamic_pie_chart_out.xlsx")) {
			        Transformer transformer = TransformerFactory.createTransformer(is, os2);
			        AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
			        List<Area> xlsAreaList = areaBuilder.build();
			        Area xlsArea = xlsAreaList.get(0);
			        Context context = new Context();
			        context.putVar("headers", headers);
			        context.putVar("data", accounts);
			        GridCommand gridCommand = (GridCommand) xlsArea.getCommandDataList().get(0).getCommand();
			        gridCommand.setProps("name,apNumbers");
			        xlsArea.applyAt(new CellRef("Template!A1"), context);
			        transformer.write();
			}
		}
		
	}

	
	private static List<Account> generateSampleAccountData() {
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("上海",100));
		accounts.add(new Account("江苏",80));
		accounts.add(new Account("陕西",60));
		accounts.add(new Account("湖南",40));
		accounts.add(new Account("江西",75));
		return accounts;
	}
}
