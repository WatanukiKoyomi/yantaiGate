package com.huadong.hdgate.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间处理的工具类
 * @author lit
 *
 */
public class DateUtils {
	public static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static DateFormat simpleSdf = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat yearMonthSdf = new SimpleDateFormat("yyyy-MM");
	public static DateFormat yearSdf = new SimpleDateFormat("yyyy");

	/**
	 * 将时间进行转换yyyy/MM/dd HH:mm:ss to yyyy-MM-dd HH:mm:ss
	 * @param time yyyy/MM/dd HH:mm:ss
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String changeTime(String time){
		String[] str = time.split("/");
		StringBuffer sb = new StringBuffer();
		sb.append(str[0]).append("-").append(Integer.parseInt(str[1])<10?"0"+str[1]:str[1]).append("-").append(Integer.parseInt(str[2])<10?"0"+str[2]:str[2]);
		return sb.toString();
	}
	/**
	 * 返回yyyy-MM-dd的时间字符串
	 * @return yyyy-MM-dd的字符串
	 */
	public static String getSimpleCurDay(){
		return simpleSdf.format(new Date());
	}
	/**
	 * 返回yyyy-MM-dd的时间字符串
	 * @return yyyy-MM-dd的字符串
	 */
	public static String getCurrentDay(){
		return sdf.format(new Date());
	}
	/**
	 * 返回当天0点的timestamp类型
	 * @return 返回timestamp类型时间
	 */
	public static Timestamp getSimpleCurTimestampDay(){
		StringBuffer beginSb = new StringBuffer();
		beginSb.append(DateUtils.getSimpleCurDay()).append(" 00:00:00");
		Timestamp beginTimestamp = Timestamp.valueOf(beginSb.toString());
		return beginTimestamp;
	}

	/**
	 * 返回当月0点的timestamp类型
	 * @return 返回timestamp类型时间
	 */
	public static Timestamp getSimpleCurMonthTimestampDay(){
		StringBuffer beginSb = new StringBuffer();
		String yearMonth = yearMonthSdf.format(new Date());
		beginSb.append(yearMonth).append("-01 00:00:00");
		Timestamp beginTimestamp = Timestamp.valueOf(beginSb.toString());
		return beginTimestamp;
	}

	/**
	 * 返回当年0点的timestamp类型
	 * @return 返回timestamp类型时间
	 */
	public static Timestamp getSimpleCurYearTimestampDay(){
		StringBuffer beginSb = new StringBuffer();
		String yearMonth = yearSdf.format(new Date());
		beginSb.append(yearMonth).append("-01-01 00:00:00");
		Timestamp beginTimestamp = Timestamp.valueOf(beginSb.toString());
		return beginTimestamp;
	}
	
	public static int getCurrentYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int yearInt = c.get(Calendar.YEAR);
		return yearInt;
	}
	public static int getCurrentMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int monthInt = c.get(Calendar.MONTH) + 1;
		return monthInt;
	}
	public static int getCurrentDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayInt = c.get(Calendar.DAY_OF_MONTH);
		return dayInt;
	}
	public static int getCurrentHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hourInt = c.get(Calendar.HOUR_OF_DAY);
		return hourInt;
	}

	/**
	 * 获取在这周的第几天，星期天：7，星期一：1，
	 * @param date
	 * @return
	 */
	public static int getDayOfWork(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWork =  c.get(Calendar.DAY_OF_WEEK) -1;
		if (dayOfWork == 0){
			return 7;
		}
		return dayOfWork;
	}

	/**
	 * 获取当月天数
	 * @return
	 */
	public static int getCurrentMonthLastDay(Date date){
	    Calendar a = Calendar.getInstance();
	    a.setTime(date);
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
	    int maxDate = a.get(Calendar.DATE);
	    return maxDate;  
	}

	public static Timestamp addYear(Timestamp val, int num) {
		return calendarAdd(val, 1, num);
	}

	public static Timestamp addMonth(Timestamp val, int num) {
		return calendarAdd(val, 2, num);
	}

	public static Timestamp addDay(Timestamp val, int num) {
		return calendarAdd(val, 5, num);
	}

	public static Timestamp addHour(Timestamp val, int num) {
		return calendarAdd(val, 11, num);
	}

	public static Timestamp addMi(Timestamp val, int num) {
		return calendarAdd(val, 12, num);
	}

	private static Timestamp calendarAdd(Timestamp val, int field, int num) {
		if (val == null) {
			return val;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(val);
		calendar.add(field, num);
		return new Timestamp(calendar.getTime().getTime());
	}

}
