/**
 * $Revision 1.1$
 * $Date 2009-07-25$
 * 
 * Copyright(C) 2009 Umessage co.,Ltd. All rights reserved.
 */
package com.reapal.common.util;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtil {

   // static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HHmm");
    //static GregorianCalendar gc = new GregorianCalendar();
   // static List<?> specialDayList = null;
    
    public static final int YEAR = GregorianCalendar.YEAR;
    public static final int MONTH = GregorianCalendar.MONTH;
    public static final int DAY = GregorianCalendar.DATE;
    public static final int HOUR = GregorianCalendar.HOUR;
    public static final int MINUTE = GregorianCalendar.MINUTE;
    public static final int SECOND = GregorianCalendar.SECOND;
    
    private static final char[] zeroArray =
        "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();

    
    
    /**
     * 判断是否周末
     */
    public static boolean isWeekend(Calendar theDay) {
        int dayOfWeek = theDay.get(Calendar.DAY_OF_WEEK);
        
        return (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY);
    }
    
    /**
     * 当前系统时间
     */
    public static Date getSysDate() {
        return new Date();
    }
    
    /**
     * 当前系统时间
     */
    public static Calendar getSysCalendar() {
        Calendar currCalendar = Calendar.getInstance();
        return currCalendar;
    }
    
    /**
     * 取得当前年的字符串形式yyyy
     */
    public static String getCurrYear(){
        return format(new Date(), "yyyy");
    }
    
    /**
     * 取得当前月的字符串形式yyyy-MM
     */
    public static String getCurrMonth(){
        return format(new Date(), "yyyy-MM");
    }
    
    /*
     * 取得当前时间的字符串形式yyyy-MM-dd
     */
    public static String getCurrDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /*
 * 取得当前时间的字符串形式yyyyMMdd
 */
    public static String getCurrDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }
    
    /*
     * 取本月第一天
     */
    public static String getFirstDayOfMonth(){
        String curMonth = getCurrMonth();
        return curMonth+"-01";
    }
    
    /*
     * 取得当前时间的字符串形式yyyy-MM-dd
     */
    public static String getCurrTime() {
        return format(new Date(),"yyyy-MM-dd HH:mm:ss");        
    }

    /*
     * 取得当前时间的字符串形式yyyyMMddHHMMSS
     */
    public static String getCurrTimeYMDHMS() {
        return format(new Date(),"yyyyMMddHHmmss");
    }
    
    /*
     *  解析字符串中的日期
     */
    public static Date parseDate(String s) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = sdf.parse(s);
        } catch (Exception e) {
        }
        return d;
    }
    
    public static Date parseDateTime(String s) {
        Date d = null;
        try {
        	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sdf2.parse(s);
        } catch (Exception e) {
        }
        return d;
    }
    
    public static Date parseStrDateTime(String s) {
        Date d = null;
        try {
        	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            d = sdf3.parse(s);
        } catch (Exception e) {
        }
        return d;
    }
    
    public static Calendar parseCalendar(String s) {
        Calendar c = null;
        Date d = parseDate(s);
        
        if (d != null) {
            c = Calendar.getInstance();
            c.setTime(d);
        } 
        return c;
    }
    
    public static Calendar parseCalendarTime(String s) {
        Calendar c = null;
        Date d = parseDateTime(s);
        
        if (d != null) {
            c = Calendar.getInstance();
            c.setTime(d);
        }
        return c;
    }
    
    public static Date parseCSTDate(String s){
        SimpleDateFormat simples = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US); 
        try{
        	Date date = simples.parse(s);
            return date;
        }catch(Exception e){
        	return null;
        }
    }
    
    /**
     *  返回日期的 "yyyy-mm-dd" 形式
     * 
     */
    public static String toString(Date d) {
        String s = null;
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            s = sdf.format(d);
        } catch (Exception e) {
        }
        return s;
    }
    
    public static String toString(Object d) {
        if (d == null) {
            return null;
        }
        if (d instanceof Date) {
            return toString((Date) d);
        }
        return null;
    }
    
    /*
     * 转换日期为指定格式的字符串

     * 用例：format(new Now(), "yyyy-MM-dd")
     *      format(new Now(), "yyyy-MM")
     */
    public static String format(Date d, String format) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.applyPattern(format);
        String s = sdf.format(d);
        return s;
    }
        
    /*
     * 转换日期和时间为指定格式的字符串
     * 用例：format(new Now(), "yyyy-MM-dd")
     *      format(new Now(), "yyyy-MM")
     *      format(new Now(), "yyyy-MM-dd hh:mm:ss")
     */
    public static String format(Calendar c, String format) {
        return format(c == null ? (Date)null : c.getTime(), format);
    }
    
    public static String format(Date d) {
        return format(d, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String format(Calendar c) {
        return format(c == null ? (Date)null : c.getTime());
    }
    
    public static String format(String s, String format) {
        Date d = parseDate(s);
        if (d == null) {
            return null;
        }
        return format(d, format);
    }
    
    public static String format2(String s, String format) {
        Date d = parseDateTime(s);
        if (d == null) {
            return null;
        }
        return format(d, format);
    }
    
    public static String format(String s) {
        return format(s, "yyyy-MM-dd");
    }
    
    /**
	 * 通用日期处理
	 * @param date
	 * @param formatPattern
	 * @return
	 */
	public static Date getStringDateTime(String date, String formatPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			if ((formatPattern == null) || formatPattern.equals("")) {
				formatPattern = "yyyy-MM-dd HH:mm:ss";
			}
			sdf.applyPattern(formatPattern);
			return sdf.parse(date);
		}
		catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将Sat Dec 01 00:00:00 CST 2007类型的日期转化为YYYY年MM月DD日
	 * @param date
	 * @return
	 */
	public static String parseDateToString(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String ret = "";
		if(date != null){
			ret = df.format(date);
		}
		return ret;
	}
	/**
	 * 将Sat Dec 01 00:00:00 CST 2007类型的日期转化为MM月DD日
	 * @param date
	 * @return
	 */
	public static String parseDateToMMDD(Date date){
		SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
		String ret = "";
		if(date != null){
			ret = df.format(date);
		}
		return ret;
	}
	
	/**
	 * 将Sat Dec 01 00:00:00 CST 2007类型的日期转化为MM月dd日 HH:mm
	 * @param date
	 * @return
	 */
	public static String parseDateTimeToString(Date date){
		SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm");
		String ret = "";
		if(date != null){
			ret = df.format(date);
		}
		return ret;
	}
	
	/**
	 * 将Sat Dec 01 00:00:00 CST 2007类型的日期转化为 HH:mm
	 * @param date
	 * @return
	 */
	public static String parseDateToHHMM(Date date){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String ret = "";
		if(date != null){
			ret = df.format(date);
		}
		return ret;
	}
	
	/**
	 * 通用日期处理
	 * @param date
	 * @param formatPattern
	 * @return String
	 */
	public static String getDateToString(Date date, String formatPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			if ((formatPattern == null) || formatPattern.equals("")) {
				formatPattern = "yyyy-MM-dd HH:mm:ss";
			}
			sdf.applyPattern(formatPattern);
			return sdf.format(date);
		}
		catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public static Date getStringDateTime(Date date, String formatPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			if ((formatPattern == null) || formatPattern.equals("")) {
				formatPattern = "yyyy-MM-dd HH:mm:ss";
			}
			sdf.applyPattern(formatPattern);
			return sdf.parse(sdf.format(date));
		}
		catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

    /*
    * 取得指定时间的字符串形式yyyy-MM-dd HH:mm:ss
    */
    public static String getDateTime(Date d) {
        return sdf2.format(d);
    }
	
	public static XMLGregorianCalendar strToXMLGregorianCalendar(Date date) {
		XMLGregorianCalendar gc = null;
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
			//throw new Exception(e+"日期转化错误"+date+";");
        }
        return gc;
    }
    
    /*
     * 例如：addDate(new Date(), UserDate.DAY, 10) -- 在当前日期上加10天
     */
    public static Date addDate(Date date, int field, int amount) {
    	GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(field, amount);
        return gc.getTime();
    }
    /*
     * 返回 d1 - d2 之间相差的天数
     */
    public static int days_between(Date d1, Date d2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();
        g1.setTime(d1);
        g2.setTime(d2);
        int zhenfu = 1;
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
            zhenfu = -1;
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        
        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);
        
        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);
        
        while (gc1.before(gc2)) {
            gc1.add(Calendar.DATE, 1);
            elapsed++;
        }
        return elapsed * zhenfu;
    }
    
    /*
     * 返回 d1 - d2 之间相差的月份数
     */
    public static int months_between(Date d1, Date d2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();
        g1.setTime(d1);
        g2.setTime(d2);
        int zhenfu = 1;
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
            zhenfu = -1;
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        
        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);
        gc1.clear(Calendar.DATE);
        
        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);
        gc2.clear(Calendar.DATE);
        
        while (gc1.before(gc2)) {
            gc1.add(Calendar.MONTH, 1);
            elapsed++;
        }
        return elapsed * zhenfu;
    }
    
    /*
     * 返回 d1 - d2 之间相差的年份
     */
    public static int years_between(Date d1, Date d2) {
        int elapsed = 0;      
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();
        if(d1!=null){g1.setTime(d1);}
        if(d2!=null){g2.setTime(d2);}
        int zhenfu = 1;
        if (g2.after(g1)) {          
            zhenfu = -1;
        }      
        
        elapsed =  g1.get(Calendar.YEAR) - g2.get(Calendar.YEAR);
        return elapsed * zhenfu;
    }
    /*
     * 返回d1-d2相差的分钟数，d1较大时为正数，否则为负数
     */
    public static int minutes_between(Date d1, Date d2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();
        g1.setTime(d1);
        g2.setTime(d2);
        int zhenfu = 1;
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
            zhenfu = -1;
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        
        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        
        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        
        while (gc1.before(gc2)) {
            gc1.add(Calendar.MINUTE, 1);
            elapsed++;
        }
        return elapsed * zhenfu;
    }
    

    /*
     * 返回d1-d2相差的秒数，d1较大时为正数，否则为负数
     */
    public static int seconds_between(Date d1, Date d2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();
        g1.setTime(d1);
        g2.setTime(d2);
        int zhenfu = 1;
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
            zhenfu = -1;
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        
        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        
        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        
        while (gc1.before(gc2)) {
            gc1.add(Calendar.SECOND, 1);
            elapsed++;
        }
        return elapsed * zhenfu;
    }
    
    //判断当前时间是否在时间date2之前
    //时间格式 2005-4-21 16:16:34
    public static boolean isDateBefore(String date2){
        try{
            Date date1 = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            return date1.before(df.parse(date2)); 
        }catch(Exception e){
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }
    
    //  得到日期的星期
    public static String getDayOfWeek(String date) {
        String str = "";
        Calendar c = parseCalendar(date);
        if (c==null) return str;
        
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day) {
        case Calendar.SUNDAY:
            str = "日";
            break;
        case Calendar.MONDAY:
            str = "一";
            break;
        case Calendar.TUESDAY:
            str = "二";
            break;
        case Calendar.WEDNESDAY:
            str = "三";
            break;
        case Calendar.THURSDAY:
            str = "四";
            break;
        case Calendar.FRIDAY:
            str = "五";
            break;
        case Calendar.SATURDAY:
            str = "六";
            break;
        default:
            str = "";
        }
        return str;
    }
    
    //  得到日期的星期
    public static String getDayOfIntWeek(Date date) {
        String str = "";
        Calendar  c = Calendar.getInstance();
        c.setTime(date) ;
        if (c==null) return str;
        
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day) {
        case Calendar.SUNDAY:
            str = "0";
            break;
        case Calendar.MONDAY:
            str = "1";
            break;
        case Calendar.TUESDAY:
            str = "2";
            break;
        case Calendar.WEDNESDAY:
            str = "3";
            break;
        case Calendar.THURSDAY:
            str = "4";
            break;
        case Calendar.FRIDAY:
            str = "5";
            break;
        case Calendar.SATURDAY:
            str = "6";
            break;
        default:
            str = "";
        }
        return str;
    }
    
    //日期段星期列表
    public static List<String> getListWeek(Date startDate,Date endDate) {
    	SimpleDateFormat sdf = new SimpleDateFormat();
		SimpleDateFormat sdf1 = new SimpleDateFormat();
		String formatPattern = "yyyy-MM-dd";
		String formatPattern1 = "MM-dd";
		sdf.applyPattern(formatPattern);
		sdf1.applyPattern(formatPattern1);
		SimpleDateFormat formatter4 = new SimpleDateFormat("E",Locale.CHINESE);
		List<String> weekList = new ArrayList<String>();
		long day=(endDate.getTime()-startDate.getTime())/(24*60*60*1000);
		for(int i=0; i<=day; i++){
			long myTime=(startDate.getTime()/1000)+60*60*24*i;
			Date myDate=new Date();
			myDate.setTime(myTime*1000);
			String week;
			try {
				week = formatter4.format(sdf.parse(sdf.format(myDate))).replaceAll("星期", "周")+"<br/>"+sdf1.format(myDate);
				weekList.add(week);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return weekList;
    }

  //日期段星期列表
    public static List<String> getSimpleWeek(Date startDate,Date endDate) {
    	SimpleDateFormat sdf = new SimpleDateFormat();
		SimpleDateFormat sdf1 = new SimpleDateFormat();
		String formatPattern = "yyyy-MM-dd";
		String formatPattern1 = "dd";
		sdf.applyPattern(formatPattern);
		sdf1.applyPattern(formatPattern1);
		SimpleDateFormat formatter4 = new SimpleDateFormat("E",Locale.CHINESE);
		List<String> weekList = new ArrayList<String>();
		long day=(endDate.getTime()-startDate.getTime())/(24*60*60*1000);
		for(int i=0; i<=day; i++){
			long myTime=(startDate.getTime()/1000)+60*60*24*i;
			Date myDate=new Date();
			myDate.setTime(myTime*1000);
			String week;
			try {
				week = formatter4.format(sdf.parse(sdf.format(myDate))).replaceAll("星期", "")+"<br/>"+sdf1.format(myDate);
				weekList.add(week);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return weekList;
    }
    
    /**
     * Formats a Date as a fifteen character long String made up of the Date's
     * padded millisecond value.
     *
     * @return a Date encoded as a String.
     */
    public static String dateToMillis(Date date) {
        return zeroPadString(Long.toString(date.getTime()), 15);
    }
    
    /**
     * Pads the supplied String with 0's to the specified length and returns
     * the result as a new String. For example, if the initial String is
     * "9999" and the desired length is 8, the result would be "00009999".
     * This type of padding is useful for creating numerical values that need
     * to be stored and sorted as character data. Note: the current
     * implementation of this method allows for a maximum <tt>length</tt> of
     * 64.
     *
     * @param string the original String to pad.
     * @param length the desired length of the new padded String.
     * @return a new String padded with the required number of 0's.
     */
    public static String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        }
        StringBuilder buf = new StringBuilder(length);
        buf.append(zeroArray, 0, length - string.length()).append(string);
        return buf.toString();
    }
    
    /**
	 * 根据传入的开始时间和截止时间，返回该时间范围内的小时列表	
	 */
	public static List<String> getBetweenHourList(String beginTime, String endTime)
	{
		List<String> list = new ArrayList<String>();
		
		if(beginTime != null &&  beginTime.length() >= 2 && endTime != null && endTime.length() >= 2)
		{
			beginTime = beginTime.substring(0,2);//开始时间
			endTime = endTime.substring(0,2); //结束时间
			
			int totalHours = Integer.parseInt(endTime) - Integer.parseInt(beginTime) + 1; //总小时数
			
			for(int i = 0; i < totalHours; i ++)
			{
				String temp = (Integer.parseInt(beginTime) + i) + "";
				if(temp.length() == 1)
				{
					list.add("0" + temp + ":00");
					
					//结束时间不生成半小时
					if(Integer.parseInt(temp) != Integer.parseInt(endTime))
						list.add("0" + temp + ":30");
				}
				else{
					list.add(temp + ":00");
					
					//结束时间不生成半小时
					if(Integer.parseInt(temp) != Integer.parseInt(endTime))
						list.add(temp + ":30");
				}
				
				
			}
		}
		
		return list;
	}

	/**
	 * @Description: 转换为中文日期
	 * @author  KEN
	 * @date 2012-8-2 下午02:56:47 
	 */
	public static String format2CnDate(String birthday) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA );
		try {
			Date date = sdf.parse(birthday);
			SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMMyy",Locale.US);			 
			return sdf1.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return "";
	}
	/**
	 * 计算年龄
	 * @return
	 */
	
	public static int getAgeYear(String outTime,String birth){
		Date outTimeDate= DateUtil.parseDate(outTime);
		Date birthDate= DateUtil.parseDate(birth);
		Calendar calOutTime=Calendar.getInstance(); //
		calOutTime.setTime(outTimeDate);
		Calendar calBirthDate=Calendar.getInstance(); //构建出生年月日
		calBirthDate.setTime(birthDate);
		int outYear=calOutTime.get(Calendar.YEAR);
		int birthYear=calBirthDate.get(Calendar.YEAR);
		int birthMonth=calBirthDate.get(Calendar.MONTH);
		int birthDay=calBirthDate.get(Calendar.DAY_OF_MONTH);
		int deletYear=outYear-birthYear;
		if(deletYear>0){
			Calendar newCalendar=Calendar.getInstance();//构建新的时间
			newCalendar.clear();
			newCalendar.set(outYear, birthMonth, birthDay);
			Date d=newCalendar.getTime();
			Date dd=calOutTime.getTime();
			if(d.after(dd)){
				deletYear=deletYear-1;
			}
		}
		return deletYear;
	}
	
	/**
	 * 取得当前时间前15天的日期，如果不到15天则将周六日除外，如果第15天正好是周一，将前两天(周六日)也包含进来
	 * @author: WANGANMIN
	 * @date:   Sep 26, 2010
	 */
	public static  Date[] getPre15Day(Date nowDate){
		
		int dayCount = 0;
		String preDateStr = "";
		String weekStr = "";
		Date preDate = null;
		Date[] resultDate = new Date[3];
		for(int j = 0; j<30; j++){
			if(j==0){
				preDateStr = DateUtil.format(nowDate);
				weekStr = DateUtil.getDayOfWeek(preDateStr);
			}else{
				preDate = DateUtil.addDate(nowDate, DateUtil.DAY, -1);
				preDateStr = DateUtil.toString(preDate);
				weekStr = DateUtil.getDayOfWeek(preDateStr);
				nowDate = preDate;
			}
			if(!"六".equals(weekStr) && !"日".equals(weekStr)){
				dayCount++;
				if(dayCount==15){
					String dateStr = DateUtil.format(nowDate);
					String weekS = DateUtil.getDayOfWeek(dateStr);
					if("一".equals(weekS)){
						resultDate[0] = nowDate;
						resultDate[1] = DateUtil.addDate(nowDate, DateUtil.DAY, -1);
						resultDate[2] = DateUtil.addDate(nowDate, DateUtil.DAY, -2);
					}else{
						resultDate[0] = nowDate;
					}
				}
			}
		}
		return resultDate;
	}
	
	/**
	 * 在day时间上加X小时
	 * @param date:yyyy-MM-dd HH:mm
	 * @param x
	 * @return
	 */
	 public static Date addHHToDate(Date date, int x) { 
 
	        if (date == null) return null; 
	        Calendar cal = Calendar.getInstance(); 
	        cal.setTime(date); 
	        cal.add(Calendar.HOUR_OF_DAY, x);
	        date = cal.getTime(); 
	        cal = null; 
	        return date; 
	    } 
	
}
