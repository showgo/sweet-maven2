package org.sweet.frameworks.foundation.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.sweet.frameworks.foundation.abstraction.AbstractObject;

/**
 * DateUtil
 * @filename:DateUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public final class DateUtil extends AbstractObject {
	private static final long serialVersionUID=4658072663969353817L;
	private static DateUtil instance=null;

	/**
	 * 私有构造函数
	 */
	private DateUtil(){
	}

	/**
	 * 获得类的单例
	 * @return
	 */
	private static DateUtil getInstance(){
		if(null==instance) {
			instance=new DateUtil();
		}
		return instance;
	}

	/**
	 * 日期解析
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	public static Date parse(String dateString,String formatString){
		Date date=null;
		try{
			SimpleDateFormat format=new SimpleDateFormat(formatString);
			date=format.parse(dateString);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期格式化
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String format(long date,String formatString){
		Date dateObject=new Date(date);
		try{
			SimpleDateFormat format=new SimpleDateFormat(formatString);
			return format.format(dateObject);
		}catch(Exception e){
			e.printStackTrace();
		}
		return String.valueOf(date);
	}

	/**
	 * 日期加减
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date add(Date date,int day){
		Calendar now=Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DAY_OF_YEAR,day);
		return now.getTime();
	}

	/**
	 * 日期相差天数
	 * @param srcDate
	 * @param dstDate
	 * @return
	 */
	public static int between(Date srcDate,Date dstDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(srcDate);
		int srcDay=cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(dstDate);
		int dstDay=cal.get(Calendar.DAY_OF_YEAR);
		return dstDay-srcDay;
	}

	/**
	 * 程序版本
	 * @return
	 */
	public static String version(){
		return version(getInstance().getClass());
	}
}
