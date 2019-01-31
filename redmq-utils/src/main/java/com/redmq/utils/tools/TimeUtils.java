package com.redmq.utils.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title 
 * @author xulz
 * @date 2019年1月28日上午10:25:16
 */
public class TimeUtils {
	private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);

	/**
	 * 返回系统时间组成的字符串。例如：2005-08-28
	 * 
	 * @return String
	 */
	public static String getSystemDate() {
		Calendar c = Calendar.getInstance();
		int y, m, d;
		String year, month, day;
		String returnValue = "";

		y = c.get(Calendar.YEAR);
		m = c.get(Calendar.MONTH) + 1;
		d = c.get(Calendar.DATE);

		year = String.valueOf(y);

		if (m <= 9) {
			month = "0" + String.valueOf(m);
		} else {
			month = String.valueOf(m);

		}
		if (d <= 9) {
			day = "0" + String.valueOf(d);
		} else {
			day = String.valueOf(d);

		}
		returnValue = year + "-" + month + "-" + day;

		return returnValue;
	}

	/**
	 * time Long值转为date
	 * 
	 * @param time
	 *            ,如"1389701337000"
	 * @return date
	 */
	public static Date parseTimeStrToDate(String time) {
		Date date = null;
		if (time.length() == 10) {
			time = time + "000";
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.valueOf(time));
		date = c.getTime();
		return date;
	}

	/**
	 * 返回日期类型 自动检测格式
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static Date StringDateClever(String str) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(str.length());
			if (str.length() == 19) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else if (str.length() == 17) {
				sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
			} else if (str.length() == 16) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (str.length() == 13) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH");
			} else if (str.length() == 10) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			} else if(str.length() == 28) {
				sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
			}
			date = sdf.parse(str);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return date;
	}

	/**
	 * 根据日期格式 返回相应日期格式的日期
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 */
	public static Date StringDateBasePattern(String datetime, String pattern) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取基准日期若干天前的日期
	 * 
	 * 
	 * @param curDate
	 *            String 基准日期
	 * @param days
	 *            int 需要获取基准日期几天前的日期
	 * 
	 * @return String
	 */
	public static String getBeforeDay(String curDate, int days) {
		String oldDate = "";
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

			if (curDate.length() == 19) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else if (curDate.length() == 16) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (curDate.length() == 13) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH");
			}

			Calendar caldate = new GregorianCalendar();
			Date date = fmt.parse(curDate);
			caldate.setTimeInMillis(date.getTime());
			caldate.add(Calendar.DATE, 0 - days);
			oldDate = fmt.format(caldate.getTime());
		} catch (Exception e) {
			return curDate;
		}
		return oldDate;
	}

	/**
	 * 获取基准日期若干天后的日期
	 * 
	 * 
	 * @param curDate
	 *            String 基准日期
	 * @param days
	 *            int 需要获取基准日期几天后的日期
	 * 
	 * @return String
	 */
	public static String getAfterDay(String curDate, int days) {
		String oldDate = "";
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

			if (curDate.length() == 19) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else if (curDate.length() == 16) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (curDate.length() == 13) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH");
			}

			Calendar caldate = new GregorianCalendar();
			Date date = fmt.parse(curDate);
			caldate.setTimeInMillis(date.getTime());
			caldate.add(Calendar.DATE, days);
			/*
			 * oldDate = caldate.get(Calendar.YEAR) + "-" +
			 * (caldate.get(Calendar.MONTH) + 1) + "-" +
			 * caldate.get(Calendar.DAY_OF_MONTH);
			 */
			oldDate = fmt.format(caldate.getTime());
		} catch (Exception e) {
			return curDate;
		}
		return oldDate;
	}

	/**
	 * 获取基准日期若干天后的日期
	 * 
	 * 
	 * @param date
	 *            Date 基准日期
	 * @param days
	 *            int 需要获取基准日期几天后的日期
	 * 
	 * @return Date
	 */
	public static Date getAfterDay(Date date, int days) {
		Calendar caldate = new GregorianCalendar();
		caldate.setTime(date);
		caldate.add(Calendar.DATE, days);
		return caldate.getTime();
	}

	/**
	 * 获取系统日期若干天前的日期
	 * 
	 * 
	 * @param days
	 *            int 需要获取系统日期几天前的日期
	 * 
	 * @return Date
	 */
	public static Date getSystemTimeBeforeDay(int days) {

		Calendar caldate = new GregorianCalendar();
		if (days >= 0)
			caldate.add(Calendar.DATE, 0 - days);
		return caldate.getTime();
	}

	/**
	 * 获取给定时间date的前day天
	 * 
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateBeforeDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		Date _date = calendar.getTime();
		return _date;
	}

	/**
	 * 获取给定时间date的前hours个小时
	 * 
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date getDateBeforHour(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -hours);
		Date _date = calendar.getTime();
		return _date;
	}
	
	/**
	 * 获取当天，0点时间
	 * @return
	 */
	public static Date getTodayDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当天，24点时间
	 * 
	 * @return
	 */
	public static Date getTodayOverDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		return calendar.getTime();
	}
	
	/**
	 * 获取当月第一天
	 * @return
	 */
	public static Date getMonthDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定月之前的月初第一天
	 * 
	 * @return
	 */
	public static Date getMonthDateBeforeMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		return calendar.getTime();
	}
	
	public static String getFormatString(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(date);
	}
	
	/**
	 * 获取几个月后的时间
	 * @param curDate
	 * @param month
	 * @return
	 */
	public static Date getAfterMonth(String curDate, int month) {
//		String oldDate = "";
		Calendar caldate = new GregorianCalendar();
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

			if (curDate.length() == 19) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else if (curDate.length() == 16) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (curDate.length() == 13) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH");
			}
			
			Date date = fmt.parse(curDate);
			caldate.setTimeInMillis(date.getTime());
			caldate.add(Calendar.MONTH, month);
//			oldDate = fmt.format(caldate.getTime());
		} catch (Exception e) {
			return caldate.getTime();
		}
		return caldate.getTime();
	}

	/**
	 * 获取几个月后的时间
	 * 
	 * @param curDate
	 * @param month
	 * @return
	 */
	public static Date getAfterMonth(Date curDate, int month) {
		Calendar caldate = new GregorianCalendar();
		try {
			caldate.setTimeInMillis(curDate.getTime());
			caldate.add(Calendar.MONTH, month);
		} catch (Exception e) {
			return caldate.getTime();
		}
		return caldate.getTime();
	}
	
	//获取当天剩余的秒数
	public static int getDayLeftSeconds() {
		int seconds = 0;//默认12小时
		Calendar calendar = new GregorianCalendar();
		long now = calendar.getTimeInMillis();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		long last = calendar.getTimeInMillis();
		if(last > now) {
			seconds = (int)(last - now) / 1000;
		}
		return seconds;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @return
	 */
	public static int getDayBetweenDate(Date afterDate, Date beforeDate) {
		long afterTime = afterDate.getTime();
		long beforeTime = beforeDate.getTime();
		if (afterTime > 0) {
			return new Long((afterTime - beforeTime) / (60 * 60 * 24 * 1000)).intValue();
		} else {
			return 0;
		}
	}

	/**
	 * Date date 获取年份
	 * 
	 * @return
	 */
	public static int getCurrentYear(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
		return Integer.parseInt(fmt.format(date));
	}

	/**
	 * 获取当前日期的年初日期
	 * 
	 * @return
	 */
	public static Date getThisYear() {
		Date date = new Date();
		try {
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
			String dateStr = year + "-" + "01-01";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			date = fmt.parse(dateStr);
		} catch (ParseException e) {
			logger.error("ParseException error", e);
		}
		return date;
	}

	/**
	 * 获取当前日期的年末日期
	 * 
	 * @return
	 */
	public static Date getThisYearEnd() {
		Date date = new Date();
		try {
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
			String dateStr = year + "-" + "12-31 23:59:59";
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = fmt.parse(dateStr);
		} catch (ParseException e) {
			logger.error("ParseException error", e);
		}
		return date;

	}

	/**
	 * 获取当前日期是星期几
	 * 
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "SUN", "MON", "TUS", "WED", "THU", "FRI", "SAT" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static void main(String[] args) {
		// System.out.println(TimeUtils.getDayLeftSeconds());
		// System.out.println("一个月后：" + getAfterMonth(new Date(), 5));
		// System.out.println("now:" + new Date());
		System.out.println(TimeUtils.getDayBetweenDate(getDateBeforeDay(new Date(), 5), new Date()));
		// String s = "[{\"pageId\":93,\"hide\":false},{\"pageId\":94,\"hide\":true}]";
		// System.out.println(StringUtil.isJsonArr(s));
		// System.out.println(StringUtil.isJson(s));
		// System.out.println(getCurrentYear(new Date()));
		// System.out.println(getThisYear());
		// System.out.println(getThisYearEnd());
		// System.out.println(getWeekOfDate(new Date()));
		System.out.println(getTodayOverDate().getTime());
	}
}
