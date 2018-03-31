package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = sf.parse(source);
				return date;
			} catch (ParseException e) {
				 throw new IllegalArgumentException("设置的字符串格式不正确"); 
			}
	  
	}

}
