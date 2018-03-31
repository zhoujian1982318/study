package beans;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateEditor extends PropertyEditorSupport {
	 public void setAsText(String text){   
	        if(text == null){  
	            throw new IllegalArgumentException("设置的字符串格式不正确");  
	        }  
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        
			try {
				Date date = sf.parse(text);
				 //②调用父类的setValue()方法设置转换后的属性对象  
		        setValue(date);   
			} catch (ParseException e) {
				 throw new IllegalArgumentException("设置的字符串格式不正确"); 
			}
	  
	        
	    }  
}
