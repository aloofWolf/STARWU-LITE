package starwu.lite.util;

import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class DateUtil {

	public static final String DEFAULT_DATEPATTERN = "yyyy-MM-dd";

	public static final String DEFAULT_TIMEPATTERN = " hh:mm:ss";

	public static final String DEFAULT_DATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String DEFAULT_DATEPATTERN_SIMP = "yyyyMMdd";

	public static final String DEFAULT_DATETIMEPATTERN_SIMP = "yyyyMMddHHmmss";

	public static final String DEFAULT_DATETIMEMSPATTERN = "yyyy-MM-dd HH:mm:ss:SSS";

	public static final String DEFAULT_DATETIMEMSPATTERN_SIMP = "yyyyMMddHHmmssSSS";

	/**
	 * 
	 * @Title: getDateString
	 * @Description: 将日期转换为字符串
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		return getDateString(date, DEFAULT_DATETIMEPATTERN);
	}

	/**
	 * 
	 * @Title: getDateString
	 * @Description: 将日期转换为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateString(Date date, String format) {
		return new SimpleDateFormat(format).format(date.getTime());
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: 将字符串转换为日期
	 * @param dateStr
	 * @return
	 */
	@SneakyThrows
	public static Date getDate(String dateStr) {
		return getDate(dateStr, DEFAULT_DATETIMEPATTERN);
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: 将字符串转换为日期
	 * @param dateStr
	 * @param format
	 * @return
	 */
	@SneakyThrows
	public static Date getDate(String dateStr, String format) {
		return new SimpleDateFormat(format).parse(dateStr);
	}

	/**
	 * 
	 * @Title: getDateNotTime
	 * @Description: 获取没有时间的日期
	 * @param date
	 * @return
	 */
	@SneakyThrows
	public static Date getDateNotTime(Date date) {
		String strDate = getDateString(date);
		return new SimpleDateFormat(DEFAULT_DATEPATTERN).parse(strDate);
	}

	/**
	 * 
	 * @Title: getDateOnlyTime
	 * @Description: 获取没有日期的时间
	 * @param date
	 * @return
	 */
	public static String getDateOnlyTime(Date date) {
		DateFormat df = DateFormat.getTimeInstance();// 只显示出时分秒
		return df.format(date);
	}

	/**
	 * 
	 * @Title: compareData
	 * @Description:日期比较
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 结束日期大于开始日期之后，返回 1 ，等于，返回 0 ；小于，返回 -1
	 */
	public static int compareData(Date begin, Date end) {
		if (end.getTime() == begin.getTime()) {
			return 0;
		} else if (end.getTime() > begin.getTime()) {
			return 1;
		} else {
			return -1;
		}

	}

	/**
	 * 
	 * @Title: getSpecifiedDayBefore
	 * @Description: 获取指定日期字符串前一天的日期字符串
	 * @param specifiedDay
	 * @return
	 */
	@SneakyThrows
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(DEFAULT_DATEPATTERN).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 
	 * @Title: getSpecifiedDayBefore
	 * @Description:根据指定日期字符串。获取前几天（由dayNume指定）的日期字符串
	 * @param specifiedDay
	 *            指定日期
	 * @param dayNum
	 *            指定天数
	 * @param format
	 *            返回日期字符串格式串
	 * @return
	 */
	@SneakyThrows
	public static String getSpecifiedDayBefore(String specifiedDay, int dayNum, String format) {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(format).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - dayNum);
		String dayBefore = new SimpleDateFormat(format).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 
	 * @Title: getSpecifiedDayBeforeDate
	 * @Description: 根据指定日期字符串获取前一天
	 * @param specifiedDay
	 * @return
	 */
	@SneakyThrows
	public static Date getSpecifiedDayBeforeDate(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(DEFAULT_DATEPATTERN).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSomeDayBefore
	 * @Description: 根据指n定日期获取前几天（由参数interval指定）的日期字符串
	 * @param date
	 * @param interval
	 * @param format
	 * @return
	 */
	public static String getSomeDayBefore(Date date, int interval, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - interval);

		String dayBefore = new SimpleDateFormat(format).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 
	 * @Title: getSomeDayBefore_beginDate
	 * @Description: 据指n定日期获取前几天（由参数interval指定）的日期开始
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getSomeDayBefore_beginDate(Date date, int interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - interval);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSomeDayBefore_endDate
	 * @Description: 据指n定日期获取前几天（由参数interval指定）的日期结束
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getSomeDayBefore_endDate(Date date, int interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - interval);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSpecifiedDayAfter
	 * @Description: 获取后一天
	 * @param specifiedDay
	 * @return
	 */
	@SneakyThrows
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(DEFAULT_DATEPATTERN).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 
	 * @Title: getSpecifiedDayAfter
	 * @Description:根据指定日期字符串。获取hou几天（由dayNume指定）的日期字符串
	 * @param specifiedDay
	 *            指定日期
	 * @param dayNum
	 *            指定天数
	 * @param format
	 *            返回日期字符串格式串
	 * @return
	 */
	@SneakyThrows
	public static String getSpecifiedDayAfter(String specifiedDay, int dayNum, String format) {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(format).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + dayNum);
		String dayAfter = new SimpleDateFormat(format).format(c.getTime());
		return dayAfter;
	}

	/**
	 * 
	 * @Title: getSpecifiedDayAfterDate
	 * @Description: 根据指定日期字符串获取后一天
	 * @param specifiedDay
	 * @return
	 */
	@SneakyThrows
	public static Date getSpecifiedDayAfterDate(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(DEFAULT_DATEPATTERN).parse(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSomeDayAfter
	 * @Description: 根据指n定日期获取后几天（由参数interval指定）的日期字符串
	 * @param date
	 * @param interval
	 * @param format
	 * @return
	 */
	public static String getSomeDayAfter(Date date, int interval, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + interval);

		String dayAfter = new SimpleDateFormat(format).format(c.getTime());
		return dayAfter;
	}

	/**
	 * 
	 * @Title: getSomeDayAfter_beginDate
	 * @Description:据指n定日期获取后几天（由参数interval指定）的日期开始
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getSomeDayAfter_beginDate(Date date, int interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + interval);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSomeDayAfter_endDate
	 * @Description:据指n定日期获取后几天（由参数interval指定）的日期结束
	 * @param date
	 * @param interval
	 * @return
	 */
	public static Date getSomeDayAfter_endDate(Date date, int interval) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + interval);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: isSameDate
	 * @Description:判断时间串与时间是否是同一天
	 * @param dateStr
	 * @param date
	 * @return
	 */
	@SneakyThrows
	public static boolean isSameDate(String dateStr, Date date) {
		Calendar strC = Calendar.getInstance();
		Calendar dateC = Calendar.getInstance();
		Date strDate = new SimpleDateFormat(DEFAULT_DATEPATTERN).parse(dateStr);
		strC.setTime(strDate);
		dateC.setTime(date);
		return strC.get(Calendar.YEAR) == dateC.get(Calendar.YEAR)
				&& strC.get(Calendar.MONTH) == dateC.get(Calendar.MONTH)
				&& strC.get(Calendar.DATE) == dateC.get(Calendar.DATE);

	}

	/**
	 * 
	 * @Title: daysBetween
	 * @Description:计算两个日期相隔天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SneakyThrows
	public static int daysBetween(Date beginDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATEPATTERN);
		beginDate = sdf.parse(sdf.format(beginDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 
	 * @Title: daysBetween
	 * @Description:计算两个日期字符串相隔天数
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	@SneakyThrows
	public static int daysBetween(String beginDateStr, String endDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATEPATTERN);
		Date beginDate = sdf.parse(beginDateStr);
		Date endDate = sdf.parse(endDateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 
	 * @Title: getSameWeekFirstDay
	 * @Description:获取指定日期获取日期同一周中的第一天(周日作为第一天)
	 * @param date
	 * @return
	 */
	public static Date getSameWeekFirstDay(Date date) {
		return getAssignedBeforeWeekFirstDay(date, 0);
	}

	/**
	 * 
	 * @Title: getSameWeekFirstDay_chinese
	 * @Description:获取指定日期获取日期同一周中的第一天(周一作为第一天)
	 * @param date
	 * @return
	 */
	public static Date getSameWeekFirstDay_chinese(Date date) {
		return getAssignedBeforeWeekFirstDay_chinese(date, 0);
	}

	/**
	 * 
	 * @Title: getAssignedBeforeWeekFirstDay
	 * @Description:获取指定日期指定几周之前（由assigned指定）的第一天(周日作为第一天)
	 * @param date
	 * @param assigned
	 * @return
	 */
	public static Date getAssignedBeforeWeekFirstDay(Date date, int assigned) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, 1);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - assigned * 7);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getAssignedBeforeWeekFirstDay_chinese
	 * @Description:获取指定日期指定几周之前（由assigned指定）的第一天(周一作为第一天)
	 * @param date
	 * @param assigned
	 * @return
	 */
	public static Date getAssignedBeforeWeekFirstDay_chinese(Date date, int assigned) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, 2);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - assigned * 7);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSameWeekLastDay
	 * @Description:获取指定日期获取日期同一周中的最后一天(周六作为最后一天)
	 * @return
	 */
	public static Date getSameWeekLastDay(Date date) {
		return getAssignedBeforeWeekLastDay(date, 0);
	}

	/**
	 * 
	 * @Title: getSameWeekLastDay_chinese
	 * @Description:获取指定日期获取日期同一周中的最后一天(周六作为最后一天)
	 * @param date
	 * @return
	 */
	public static Date getSameWeekLastDay_chinese(Date date) {
		return getAssignedBeforeWeekLastDay_chinese(date, 0);
	}

	/**
	 * 
	 * @Title: getAssignedBeforeWeekLastDay
	 * @Description:获取指定日期指定几周之前（由assigned指定）的最后天(周六作为最后一天)
	 * @param date
	 * @param assigned
	 * @return
	 */
	public static Date getAssignedBeforeWeekLastDay(Date date, int assigned) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, 7);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - assigned * 7);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getAssignedBeforeWeekLastDay_chinese
	 * @Description:获取指定日期指定几周之前（由assigned指定）的最后天(周日作为最后一天)
	 * @param date
	 * @param assigned
	 * @return
	 */
	public static Date getAssignedBeforeWeekLastDay_chinese(Date date, int assigned) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, 1);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - (assigned - 1) * 7);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSameMonthFirstDay
	 * @Description:获取指定日期当月第一天
	 * @param date
	 * @return
	 */
	public static Date getSameMonthFirstDay(Date date) {
		return getAssignedBeforeMonthFirstDay(date, 0);
	}

	/**
	 * 
	 * @Title: getSameMonthLastDay
	 * @Description:获取指定日期当月最后一天
	 * @param date
	 * @return
	 */
	public static Date getSameMonthLastDay(Date date) {
		return getAssignedBeforeMonthLaseDay(date, 0);
	}

	/**
	 * 
	 * @Title: getAssignedBeforeMonthFirstDay
	 * @Description:获取指定日期指定之前月（由assigned指定）的第一天
	 * @param date
	 * @param assigned
	 * @return
	 */
	public static Date getAssignedBeforeMonthFirstDay(Date date, int assigned) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - assigned);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getAssignedBeforeMonthLaseDay
	 * @Description:获取指定日期指定之前月（由assigned指定）的最后一天
	 * @param date
	 * @param assigned
	 * @return
	 */
	public static Date getAssignedBeforeMonthLaseDay(Date date, int assigned) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1 - assigned);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getDateAfterMinute
	 * @Description:获取几分钟后的时间
	 * @param minute
	 * @return
	 */
	public static Date getDateAfterMinute(int minute) {
		long currentTime = System.currentTimeMillis() + minute * 60 * 1000;
		return new Date(currentTime);
	}

	/**
	 *
	 * @Title: getDateBeforeMinute
	 * @Description:获取几分钟后的时间
	 * @param minute
	 * @return
	 */
	public static Date getDateBeforeMinute(int minute) {
		long currentTime = System.currentTimeMillis() - minute * 60 * 1000;
		return new Date(currentTime);
	}
}
