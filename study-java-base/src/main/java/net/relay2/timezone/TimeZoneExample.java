package net.relay2.timezone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeZoneExample {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2016);
		c.set(Calendar.MONTH, 2);
		c.set(Calendar.DAY_OF_MONTH, 13);
		c.set(Calendar.HOUR_OF_DAY, 2);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
//		Calendar c = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("America/Denver");
		System.out.println(tz.toString());
		c.setTimeZone(tz);
//		c.setTimeInMillis(1457859599999L);
////		System.out.println(tz.useDaylightTime());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(tz);
		System.out.println(sf.format(c.getTime()));
//		
//		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH);
//		int day  = c.get(Calendar.DAY_OF_MONTH);
//		int hour  = c.get(Calendar.HOUR_OF_DAY);
//		int minute  = c.get(Calendar.MINUTE);
//		int second  = c.get(Calendar.SECOND);
//		String test = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+ second;
//		System.out.println(test);
//		
//		c.setTimeInMillis(1457859600000L);
//		
//		 year = c.get(Calendar.YEAR);
//		 month = c.get(Calendar.MONTH);
//		 day  = c.get(Calendar.DAY_OF_MONTH);
//		 hour  = c.get(Calendar.HOUR_OF_DAY);
//		 minute  = c.get(Calendar.MINUTE);
//		 second  = c.get(Calendar.SECOND);
//		 String test1 = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+ second;
//		 System.out.println(test1);
//		TimeZone tz = TimeZone.getTimeZone("Asia/Bahrain");
//		System.out.println(tz.useDaylightTime());
//		
//		tz = TimeZone.getTimeZone("Australia/Perth");
//		System.out.println(tz.useDaylightTime());
	}

}
