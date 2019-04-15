package com.zy.study.etl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 日期处理
 * 
 * @date 2017-08-01 17:49:31
 */
public class DateUtils {
	
	private DateUtils() {
		throw new IllegalStateException("Utility class");
	}

	/** 时间格式(yyyyMMdd) */
	public static final String DATE_PATTERN_EIGHT = "yyyyMMdd";
	
	/** 时间格式(HHmmss) */
	public static final String DATE_PATTERN_SIX = "HHmmss";
	
	/** 时间格式(yyyy-MM-dd) */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	/** 时间格式(yyyy年MM月dd日) */
	public static final String DATE_PATTERN_2 = "yyyy年MM月dd日";
	

	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 时间格式(yyyy/M/d hh:mm:ss) */
	public static final String DATE_TIME_PATTERN_1 = "yyyy/M/d hh:mm:ss";

	/** 时间格式(yyyyMMddHHmmss) */
	public static final String DATE_TIME_PATTERN_2 = "yyyyMMddHHmmss";
	
	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String formatTimeStampToString(String timeStamp){
		return new SimpleDateFormat(DATE_TIME_PATTERN).format((new Date(Long.parseLong(timeStamp) * 1000)));
	}

	/**
	 * 判断当前日期是否有效
	 * @param startdate --String*-- 日期区间开始（格式同pattern）
	 * @param enddate --String*-- 日期区间截止（格式同pattern）
	 * @param pattern --String-- 时间格式（默认为yyyy-MM-dd）
	 * @return true表示有效
	 */
	public static boolean isCurrentDateValid(String startdate, String enddate, String pattern){
		if (StringUtils.isBlank(startdate) || StringUtils.isBlank(enddate)){
			return false;
		}
		
		Date startDate = formatDate(startdate, StringUtils.isBlank(pattern) ? DATE_PATTERN : pattern);
		Date endDate = formatDate(enddate, StringUtils.isBlank(pattern) ? DATE_PATTERN : pattern);
		
		long start = Long.parseLong(format(startDate, DATE_PATTERN_EIGHT));
		long end = Long.parseLong(format(endDate, DATE_PATTERN_EIGHT));
		long current = Long.parseLong(getCurrentDate(DATE_PATTERN_EIGHT));
		
		if (start <= current && current <= end){
			return true;
		}
		
		return false;
	}
	

	/**
	 * 格式化时间
	 * @param time --String*-- 指定时间
	 * @param pattern --String*-- 时间格式
	 * @return null 或 时间对象
	 */
	public static Date formatDate(String time, String pattern){
		if (StringUtils.isBlank(time) || StringUtils.isBlank(pattern)){
			return null;
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.parse(time);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 格式化指定时间
	 * @param date --Date*-- 指定时间对象
	 * @param pattern --String*-- 时间格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	public static Date getCurrentSystemTime(){
		return getCurrentSystemTimeByTimeZone(TimeZone.getTimeZone("CTT"));//GMT+8
	}
	
	/**
	 * 获取当前系统时间
	 * @param timeZone --TimeZone*-- 时区对象
	 * @return 时间对象
	 */
	public static Date getCurrentSystemTimeByTimeZone(TimeZone timeZone){
		return Calendar.getInstance(timeZone).getTime();
	}
	
	/**
	 * 格式化当前时间
	 * @param pattern --String*-- 时间格式
	 * @return
	 */
	public static String getCurrentDate(String pattern){
		return format(getCurrentSystemTime(), pattern);
	}
	
    //-----------------------------------------------------------------------
    /**
     * Adds a number of years to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of months to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of weeks to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of days to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of hours to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of minutes to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of seconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of milliseconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param calendarField  the calendar field to add to
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
	
}