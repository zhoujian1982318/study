package views;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class UserPdfView extends AbstractPdfView {
	// now spring only support itext is lower, if we want to support highest version of itext (above 5.0)
	//we may need to write new object AbstractIText5PdfView  extends  AbstractView
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		 List<String> list = (List<String>) model.get("list");
		//显示中文  
	    // BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);   
	    // com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL );  
		 PdfPTable table = new PdfPTable(1);  
         table.setSpacingBefore(20);  
         table.setSpacingAfter(30);
         table.addCell("title"); 
		 for (Iterator<String> iter = list.iterator(); iter.hasNext();) {  
	          String element = iter.next();
	          table.addCell(element); 
	         // document.add(new Paragraph(element)); 
	     }  
		 document.add(table);
		 
		 document.close();
	}

}
