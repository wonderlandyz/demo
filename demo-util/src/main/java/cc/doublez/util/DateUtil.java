package cc.doublez.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: Description: 此类实现封装日期常用操作
 * 
 * @author zyfeng
 * @version 1.0
 */
public class DateUtil {
	protected static Log LOG = LogFactory.getLog(DateUtil.class);
	/**
	 * @since liushen @ Mar 8, 2011
	 */
	public static final String STR_YYYYMMDD_HH = "yyyy-MM-dd HH";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_MONTH_PATTERN = "yyyy-MM";
	/**
	 * 默认TPP的时间格式
	 */
	public static final String FMT_TPP_yMdHm = "yyyy.MM.dd HH:mm";

	public static final String FMT_TPP_yMdHms = "yyyy.MM.dd HH:mm:ss";
	/**
	 * 默认TPP的长时间格式
	 */
	public static final String FMT_TPP_yMd = "yyyy.MM.dd";

	/**
	 * 一天的总秒数
	 */
	public static final long ONE_DAY_SECONDS = 24 * 3600;
	/**
	 * 一小时的总秒数
	 */
	public static final long ONE_HOUR_SCONDS = 3600;
	/**
	 * 一分钟的总秒数
	 */
	public static final long ONE_MINUTE_SCONDS = 60;

	// 格式：年－月－日 小时：分钟：秒
	public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

	// 格式：年－月－日 小时：分钟
	public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

	// 格式：年月日 小时分钟秒
	public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

	public static final String FORMAT_FOUR = "yyyyMMdd";

	public static final String FORMAT_MONTH = "yyyyMM";

	public static final String FORMAT_FIVE = "yyyyMMddHHmmss";

	public static final String FORMAT_SIX = "yyyy/MM/dd HH:mm:ss";

	// 格式：年－月－日
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
	// 格式：年－月－日
	public static final String SHOW_DATE_FORMAT = "yyyy/MM/dd";

	// 格式：月－日
	public static final String SHORT_DATE_FORMAT = "MM-dd";

	// 格式：小时：分钟：秒
	public static final String LONG_TIME_FORMAT = "HH:mm:ss";

	// 格式：小时：分钟
	public static final String LONG_TIME_MINUTE_FORMAT = "HH:mm:ss";

	// 年的加减
	public static final int SUB_YEAR = Calendar.YEAR;

	// 月加减
	public static final int SUB_MONTH = Calendar.MONTH;

	// 天的加减
	public static final int SUB_DAY = Calendar.DATE;

	// 小时的加减
	public static final int SUB_HOUR = Calendar.HOUR;

	// 分钟的加减
	public static final int SUB_MINUTE = Calendar.MINUTE;

	// 秒的加减
	public static final int SUB_SECOND = Calendar.SECOND;

	public static final String TIME_OLD = "1900-01-01 00:00:00";

	// 用来全局控制 上一周，本周，下一周的周数变化
	private int weeks = 0;

	private int MaxDate;// 一月最大天数

	private int MaxYear;// 一年最大天数

	/**
	 * 默认格式.
	 */
	public final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 默认日期格式.
	 */
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 默认时间格式.
	 */
	public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	/**
	 * 精确到小时的时间格式.
	 */
	public final static SimpleDateFormat TIMEAREA_FORMAT = new SimpleDateFormat(STR_YYYYMMDD_HH);
	/**
	 * 精确到分钟的时间格式.
	 */
	public final static SimpleDateFormat TIMEAREA_MIN_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public DateUtil() {
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date StringtoDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			// log.error(e);
			d = null;
		}
		return d;
	}

	public static String format(String dateStr, String format) {
		Date date = StringtoDate(dateStr, format);
		return DateToString(date, format);
	}

	/**
	 * 按指定格式格式化时期时间
	 * 
	 * @param date
	 *            date
	 * @param format
	 *            format
	 * @return string.
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return date == null ? "" : formater.format(date);
	}

	/**
	 * 按指定格式格式化时期时间
	 * 
	 * @param date
	 *            date
	 * @return string.
	 */
	public static String format(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
		return date == null ? "" : formater.format(date);
	}

	/**
	 * 按指定格式解析字符串，将字符串转为日期时间格式
	 * 
	 * @param str
	 *            string
	 * @param format
	 *            format
	 * @return date
	 */
	public static Date parse(String str, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			return formater.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */
	public static Date StringtoDate(String dateStr, String format, ParsePosition pos) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr, pos);
		} catch (Exception e) {
			// log.error(e);
			d = null;
		}
		return d;
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToString(Date date, String format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			// log.error(e);
		}
		return result;
	}

	/**
	 * 获取当前时间的指定格式
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format) {
		return DateToString(new Date(), format);
	}

	/**
	 * @param dateStr
	 * @param amount
	 * @return
	 */
	public static String DateSub(int dateKind, String dateStr, int amount) {
		Date date = StringtoDate(dateStr, FORMAT_ONE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(dateKind, amount);
		return DateToString(calendar.getTime(), FORMAT_ONE);
	}

	/**
	 * 两个日期相减
	 * 
	 * @param firstTime
	 * @param secTime
	 * @return 相减得到的秒数
	 */
	public static long TimeSub(String firstTime, String secTime) {

		long first = StringtoDate(firstTime, FORMAT_ONE).getTime();
		long second = StringtoDate(secTime, FORMAT_ONE).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 获得某月的天数
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @return int
	 */
	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8")
				|| month.equals("10") || month.equals("12")) {
			days = 31;
		} else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
			days = 30;
		} else {
			if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
					|| Integer.parseInt(year) % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}

		return days;
	}

	/**
	 * 获取某年某月的天数
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int 月份[1-12]
	 * @return int
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前日期
	 * 
	 * @return int
	 */
	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	 * 
	 * @return int
	 */
	public static int getToMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年份
	 * 
	 * @return int
	 */
	public static int getToYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的天
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回日期的年
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的月份，1-12
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 * 
	 * @param date1
	 *            Date
	 * @param date2
	 *            Date
	 * @return long
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}

	/**
	 * 比较两个日期的年差
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static int yearDiff(String before, String after) {
		Date beforeDay = StringtoDate(before, LONG_DATE_FORMAT);
		Date afterDay = StringtoDate(after, LONG_DATE_FORMAT);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较指定日期与当前日期的差
	 * 
	 * @param after
	 * @return
	 */
	public static int yearDiffCurr(String after) {
		Date beforeDay = new Date();
		Date afterDay = StringtoDate(after, LONG_DATE_FORMAT);
		return getYear(beforeDay) - getYear(afterDay);
	}

	public static int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得当前日期字符串，格式"yyyy_MM_dd_HH_mm_ss"
	 * 
	 * @return
	 */
	public static String getCurrent() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		StringBuffer sb = new StringBuffer();
		sb.append(year).append("_").append(StringUtil.addzero(month, 2)).append("_").append(StringUtil.addzero(day, 2))
				.append("_").append(StringUtil.addzero(hour, 2)).append("_").append(StringUtil.addzero(minute, 2))
				.append("_").append(StringUtil.addzero(second, 2));
		return sb.toString();
	}

	/**
	 * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String getNow() {
		Calendar today = Calendar.getInstance();
		return DateToString(today.getTime(), FORMAT_ONE);
	}

	public static String getNowPatten() {
		Calendar today = Calendar.getInstance();
		return DateToString(today.getTime(), FMT_TPP_yMd);
	}
	/**
	 * 获得（当前日期+N天）字符串，格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @author liyang @ 2016年5月20日 下午3:08:02
	 * @param addDays
	 * @return
	 */
	public static String getNow(int addDays) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, addDays);
		return DateToString(today.getTime(), FORMAT_ONE);
	}

	/**
	 * 根据生日获取星座
	 * 
	 * @param birth
	 *            YYYY-mm-dd
	 * @return
	 */
	public static String getAstro(String birth) {
		if (!isDate(birth)) {
			birth = "2000" + birth;
		}
		if (!isDate(birth)) {
			return "";
		}
		int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1, birth.lastIndexOf("-")));
		int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
		LOG.debug(month + "-" + day);
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(start, start + 2) + "座";
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 * 
	 * @param date
	 *            YYYY/mm/dd
	 * @return
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))/?((((0?");
		reg.append("[13578])|(1[02]))/?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))/?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2/?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))/?((((0?[13578])|(1[02]))");
		reg.append("/?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("/?((0?[1-9])|([1-2][0-9])|(30)))|(0?2/?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 * 
	 * @param date
	 *            YYYY-mm-dd
	 * @return
	 */
	public static boolean isDateStr(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/**
	 * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
	 * 
	 * @param date
	 *            日期 为null时表示当天
	 * @param months
	 *            相加(相减)的月数
	 */
	public static Date nextMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 取得指定日期过 day 天后的日期 (当 day 为负数表示指定月之前);
	 * 
	 * @param date
	 *            日期 为null时表示当天
	 * @param day
	 *            相加(相减)的月数
	 */
	public static Date nextDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}

	public static String nextDay(String dateString, int day, String dateFormat) {
		Date date = stringToDate(dateString, dateFormat);
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DAY_OF_YEAR, day);
		return DateToString(cal.getTime(), dateFormat);
	}

	/**
	 * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
	 * 
	 * @param date
	 *            日期 为null时表示当天
	 */
	public static Date nextWeek(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.WEEK_OF_MONTH, week);
		return cal.getTime();
	}

	public static int compare_date(String date1, Date date2) {
		System.out.println(date1);
		DateFormat df = new SimpleDateFormat(FMT_TPP_yMd);
		try {
			Date dt1 = df.parse(date1);
			if (dt1.getTime() > date2.getTime()) {
				return 1;
			} else if (dt1.getTime() < date2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取当前的日期
	 * 
	 * @return
	 */
	public static String currDay() {
		return DateUtil.DateToString(new Date(), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 获取昨天的日期
	 * 
	 * @return
	 */
	public static String befoDay() {
		return DateUtil.DateToString(DateUtil.nextDay(new Date(), -1), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 获取明天的日期
	 */
	public static String afterDay() {
		return DateUtil.DateToString(DateUtil.nextDay(new Date(), 1), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 取得当前时间距离1900/1/1的天数
	 * 
	 * @return
	 */
	public static int getDayNum() {
		int daynum = 0;
		GregorianCalendar gd = new GregorianCalendar();
		Date dt = gd.getTime();
		GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
		Date dt1 = gd1.getTime();
		daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
		return daynum;
	}

	/**
	 * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
	 * 
	 * @param day
	 * @return
	 */
	public static Date getDateByNum(int day) {
		GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
		Date date = gd.getTime();
		date = nextDay(date, day);
		return date;
	}

	/**
	 * *************************************************************************
	 * ***************
	 */

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得上月最后一天的日期
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// 获得本年有多少天
	public int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	/**
	 * 获取某个时间的的当月的最后一天
	 * 
	 * @param datetime
	 * @return
	 * @throws ParseException
	 */
	public static String getDateEndDay(String datetime) throws ParseException {

		SimpleDateFormat dateS = new SimpleDateFormat("yyyy-MM-dd");

		Date temp = dateS.parse(datetime);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式

		String years = dateFormat.format(temp);
		SimpleDateFormat df = new SimpleDateFormat("MM");// 可以方便地修改日期格式
		String months = df.format(temp);
		datetime = years + "-" + months + "-"
				+ getLastDayOfMonth(Integer.valueOf(years).intValue(), Integer.valueOf(months).intValue());
		return datetime;
	}

	// 获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	// 获得参数月的本季度第一天
	public String getThisSeasonFirstDay(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		return years_value + "-" + start_month + "-" + start_days;

	}

	// 获得本季度
	public String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
				+ "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public static void main(String[] args) {

		// DateUtil du = new DateUtil();
		// logger.info("获取当天日期:" + du.getNowTime("yyyy-MM-dd"));
		// logger.info("获取本周一日期:" + du.getMondayOFWeek());
		// logger.info("获取本周日的日期~:" + du.getCurrentWeekday());
		// logger.info("获取上周一日期:" + du.getPreviousWeekday());
		// logger.info("获取上周日日期:" + du.getPreviousWeekSunday());
		// logger.info("获取下周一日期:" + du.getNextMonday());
		// logger.info("获取下周日日期:" + du.getNextSunday());
		// logger.info("获得相应周的周六的日期:" + du.getNowTime("yyyy-MM-dd"));
		// logger.info("获取本月第一天日期:" + du.getFirstDayOfMonth());
		// logger.info("获取本月最后一天日期:" + du.getDefaultDay());
		// logger.info("获取上月第一天日期:" + du.getPreviousMonthFirst());
		// logger.info("获取上月最后一天的日期:" + du.getPreviousMonthEnd());
		// logger.info("获取下月第一天日期:" + du.getNextMonthFirst());
		// logger.info("获取下月最后一天日期:" + du.getNextMonthEnd());
		// logger.info("获取本年的第一天日期:" + du.getCurrentYearFirst());
		// logger.info("获取本年最后一天日期:" + du.getCurrentYearEnd());
		// logger.info("获取去年的第一天日期:" + du.getPreviousYearFirst());
		// logger.info("获取去年的最后一天日期:" + du.getPreviousYearEnd());
		// logger.info("获取明年第一天日期:" + du.getNextYearFirst());
		// logger.info("获取明年最后一天日期:" + du.getNextYearEnd());
		// logger.info("获取本季度第一天到最后一天:" + du.getThisSeasonTime(11));
		// logger.info("获取本季度第一天:" +
		// du.getThisSeasonTime(DateUtil.getToMonth()).split(";"));
		// logger.info("获取两个日期之间间隔天数2008-12-1~2008-9.29:"
		// + DateUtil.getTwoDay("2008-12-1", "2008-9-29"));
		//
		// String birth = "2008-02-31";
		// logger.debug(getAstro(birth));

		System.out.println(DateUtil.compare_date("2014-01-18", new Date()));

	}

	public static Date addDays(Date start, int addDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.DAY_OF_YEAR, addDays);
		return cal.getTime();
	}

	public static int subMonth(String dateTime, Date date) {
		if (StringUtil.isBlank(dateTime)) {
			return 0;
		}
		Date startDate = StringtoDate(dateTime, SHOW_DATE_FORMAT);
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(startDate);
		int c = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
				- cal2.get(Calendar.MONTH);
		return c;
	}

	public static String timeStamp2Date(long timestamp, String formats) {
		String date = new SimpleDateFormat(formats).format(new Date(timestamp));
		return date;
	}

	/**
	 * 判断srcDate是否晚于comparedDate 1:晚于 ;0：等于 ;-1:早于;-2:异常
	 * 
	 * @param srcDate
	 *            date need to compare
	 * @param comparedDate
	 *            date which is compared
	 * @return true/false
	 */
	public static int compare(String srcDate, String comparedDate) {
		return compare(srcDate, comparedDate, DateUtil.FORMAT_FOUR);
	}

	/**
	 * 判断srcDate是否晚于comparedDate 1:晚于 ;0：等于 ;-1:早于;-2:异常
	 * 
	 * @param srcDate
	 *            date need to compare
	 * @param comparedDate
	 *            date which is compared
	 * @param pattern
	 *            pattern
	 * @return true/false
	 */
	public static int compare(String srcDate, String comparedDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date sDate = null;
		Date compDate = null;
		try {
			sDate = sdf.parse(srcDate);
			compDate = sdf.parse(comparedDate);
		} catch (ParseException e) {
			return -2;
		}
		if (sDate.after(compDate)) {
			return 1;
		} else if (sDate.before(compDate)) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 转换时间成毫秒
	 * 
	 * @param time
	 *            待转换的时间
	 * @param pattern
	 *            与time一致的时间格式
	 * @return 转换后的毫秒数
	 * @since fangxiang @ Apr 17, 2010
	 */

	public static final long getDateAsMillis(String time, String pattern) {
		if (time == null) {
			return -1;
		}
		Date theTime = stringToDate(time, (pattern == null) ? DEFAULT_TIME_PATTERN : pattern);
		if (theTime == null) {
			return -1;
		}
		return theTime.getTime();
	}

	/**
	 * 将使用的毫秒数转化为可读的字符串, 如1天1小时1分1秒. <BR>
	 * <code>assertEquals("1天1小时1分1秒", DateUtil.timeToString(90061000));</code>
	 * 
	 * @param msUsed
	 *            使用的毫秒数.
	 * @return 可读的字符串, 如1天1小时1分1秒.
	 */
	public static String timeToString(long msUsed) {
		if (msUsed < 0) {
			return String.valueOf(msUsed);
		}
		if (msUsed < 1000) {
			return String.valueOf(msUsed) + "毫秒";
		}
		// 长于1秒的过程，毫秒不计
		msUsed /= 1000;
		if (msUsed < 60) {
			return String.valueOf(msUsed) + "秒";
		}
		if (msUsed < 3600) {
			long nMinute = msUsed / 60;
			long nSecond = msUsed % 60;
			return String.valueOf(nMinute) + "分" + String.valueOf(nSecond) + "秒";
		}
		// 3600 * 24 = 86400
		if (msUsed < 86400) {
			long nHour = msUsed / 3600;
			long nMinute = (msUsed - nHour * 3600) / 60;
			long nSecond = (msUsed - nHour * 3600) % 60;
			return String.valueOf(nHour) + "小时" + String.valueOf(nMinute) + "分" + String.valueOf(nSecond) + "秒";
		}

		long nDay = msUsed / 86400;
		long nHour = (msUsed - nDay * 86400) / 3600;
		long nMinute = (msUsed - nDay * 86400 - nHour * 3600) / 60;
		long nSecond = (msUsed - nDay * 86400 - nHour * 3600) % 60;
		return String.valueOf(nDay) + "天" + String.valueOf(nHour) + "小时" + String.valueOf(nMinute) + "分"
				+ String.valueOf(nSecond) + "秒";
	}


	/**
	 * 取本周一.
	 * 
	 * @return 本周一
	 */
	public static Calendar getThisMonday() {
		return getThatMonday(Calendar.getInstance());
	}

	/**
	 * 获取cal所在周的周一.
	 * 
	 * @param cal
	 *            给定日期
	 * @return cal所在周的周一
	 */
	public static Calendar getThatMonday(Calendar cal) {
		int n = cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
		cal.add(Calendar.DATE, n);
		return cal;
	}

	/**
	 * 取本周日.
	 * 
	 * @return 本周日
	 */
	public static Calendar getThisSunday() {
		return getThatSunday(Calendar.getInstance());
	}

	/**
	 * 获取cal所在周的周日.
	 * 
	 * @param cal
	 *            给定日期
	 * @return cal所在周的周日
	 */
	public static Calendar getThatSunday(Calendar cal) {
		int n = (Calendar.SUNDAY + 7) - cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, n);
		return cal;
	}

	/**
	 * 获取两个日期相差的天数.
	 * 
	 * @return 两个日期相差的天数.
	 */
	public static int minus(Calendar cal1, Calendar cal2) {
		if (cal1.after(cal2)) {
			int nBase = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 365;
			return nBase + cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR);
		}
		return minus(cal2, cal1);
	}

	/**
	 * 获取当前年份.
	 * 
	 * @since liushen @ Jun 13, 2011
	 */
	public static int getThisYear() {
		return getYear(System.currentTimeMillis());
	}

	/**
	 * 获取当前年份.
	 * 
	 * @since liushen @ Jun 13, 2011
	 */
	public static int getYear(long timestamp) {
		Date now = new Date(timestamp);
		Calendar cal = toCalendar(now);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 从Date对象得到Calendar对象. <BR>
	 * JDK提供了Calendar.getTime()方法, 可从Calendar对象得到Date对象,
	 * 但没有提供从Date对象得到Calendar对象的方法.
	 * 
	 * @param date
	 *            给定的Date对象
	 * @return 得到的Calendar对象. 如果date参数为null, 则得到表示当前时间的Calendar对象.
	 */
	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		return cal;
	}

	/**
	 * 获取当前的时间（毫秒）
	 * 
	 * @return 当前的时间，以毫秒为单位
	 * @since fangxiang @ May 15, 2010
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前系统时间的毫秒数，可以指定想要获取的时间格式<br>
	 * 如需要获取当前系统时间精确到日期的毫秒数，则时间格式为yyyy-MM-dd<br>
	 * 如需要获取当前系统时间精确到小时的毫秒数，则时间格式为yyyy-MM-dd HH<br>
	 * 若传入null或者空或者不符合规定的格式，则返回精确到毫秒的时间，即System.currentTimeMillis()
	 * 
	 * @param dateFormat
	 *            指定当前时间的格式
	 * @return
	 * @since zhangshi @ 2011-11-11
	 */
	public static long getCurrentTimeMillisWithFormat(String dateFormat) {
		if (StringUtil.isEmpty(dateFormat))
			return getCurrentTimeMillis();

		String date = DateUtil.date2String(new Date(), dateFormat);
		if (null == date)
			return getCurrentTimeMillis();

		long currentSystemTimeWithFormat = DateUtil.getDateAsMillis(date, dateFormat, 0);
		return currentSystemTimeWithFormat;
	}

	/**
	 * 完成日期串到日期对象的转换. <BR>
	 * 
	 * @param dateString
	 *            日期字符串
	 * @param dateFormat
	 *            日期格式
	 * @return date 日期对象
	 */
	public static Date stringToDate(String dateString, String dateFormat) {
		if ("".equals(dateString) || dateString == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(dateFormat).parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取和指定cal对象相隔指定天数的cal对象. 大于0表示之后, 小于0表之前.
	 * 
	 * @param cal
	 *            指定cal对象
	 * @param relativeDay
	 *            相隔指定天数
	 * @return cal对象
	 */
	public static Calendar getCalendar(Calendar cal, int relativeDay) {
		cal.add(Calendar.DATE, relativeDay);
		return cal;
	}

	/**
	 * 获取和当天相隔指定天数的Date对象. 大于0表示之后, 小于0表之前.
	 * 
	 * @param relativeDay
	 *            相隔指定天数
	 * @return Date对象
	 * @see #getCalendar(Calendar, int)
	 */
	public static Date getDate(int relativeDay) {
		return getCalendar(Calendar.getInstance(), relativeDay).getTime();
	}

	public static String date2String(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 检查一个时间是否接近于一个时间。
	 * 
	 * @param date
	 *            要比较的时间
	 * @param baseDate
	 *            基础时间
	 * @param seconds
	 *            秒数
	 * 
	 * @return 如果 date 在 baseDate前后 seonds 秒数，则返回true，否则返回false。
	 */
	public static boolean isDateClose(Date date, Date baseDate, int seconds) {

		long m_time = date.getTime();
		long b_time = baseDate.getTime();
		long ms = seconds * 1000;

		if (m_time == b_time)
			return true;

		if (m_time > b_time) { // 现在时间在基础时间之前
			return b_time + ms > m_time;
		} else if (m_time < b_time) { // 现在时间在基础时间之后
			return m_time + ms > b_time;
		} else { // 同一个时间
			return true;
		}
	}

	/**
	 * 转换时间成毫秒
	 * 
	 * @param time
	 *            待转换的时间
	 * @param pattern
	 *            与time一致的时间格式
	 * @return 转换后的毫秒数
	 * @since fangxiang @ Apr 17, 2010
	 */

	public static final long getDateAsMillis(String time, String pattern, int deltaDay) {
		if (time == null) {
			return -1;
		}
		Date theTime = stringToDate(time, (pattern == null) ? DEFAULT_TIME_PATTERN : pattern);
		if (theTime == null) {
			return -1;
		}
		//
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(theTime);
		calendar.add(Calendar.DAY_OF_MONTH, deltaDay);
		//
		return theTime.getTime();
	}

	/**
	 * 转换时间成毫秒
	 * 
	 * @param time
	 *            待转换的时间
	 * @return 转换后的毫秒数
	 * @since fangxiang @ Apr 17, 2010
	 */
	public static final long getDateAsMillis(String time) {
		return getDateAsMillis(time, DEFAULT_TIME_PATTERN);
	}

	/**
	 * 将Date类型转换成时间表达形式
	 * 
	 * @param date
	 *            待转换的Date类型
	 * @return 转换后的时间表达形式
	 * @since fangxiang @ Apr 17, 2010
	 */
	public static final String date2String(Date date) {
		return date2String(date, DEFAULT_TIME_PATTERN);
	}

	/**
	 * 将整数型的时间转换成字符串格式的时间
	 * 
	 * @param dateMillis
	 *            毫秒时间
	 * @param pattern
	 *            字符串的格式
	 * @return 转换后的时间字符串
	 * @since fangxiang @ May 31, 2010
	 */
	public static final String millis2String(long dateMillis, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date(dateMillis));
	}

	/**
	 * 获取指定月份起始点的毫秒数
	 * 
	 * @param date
	 *            指定月份
	 * @param pattern
	 *            时间格式
	 * @return 月份起始点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getMonthStartAsMillis(String date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(date, pattern));
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		return cal.getTime().getTime();
	}

	/**
	 * 获取指定月份结束点的毫秒数
	 * 
	 * @param date
	 *            指定月份
	 * @param pattern
	 *            时间格式
	 * @return 月份结束点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getMonthEndAsMillis(String date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(date, pattern));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime().getTime();
	}

	/**
	 * 获取指定日期结束点的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @param pattern
	 *            时间格式
	 * @return 日期起始点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getDayEndAsMillis(String date, String pattern) {
		Calendar current = toCalendar(stringToDate(date, pattern));
		current.roll(Calendar.DAY_OF_MONTH, true);
		return current.getTimeInMillis();
	}

	/**
	 * 获取下一天的间隔
	 * 
	 * @return
	 * @since fangxiang @ Dec 23, 2010
	 */
	public static final long getDayDurationAsMillis(int duration) {
		Calendar current = Calendar.getInstance();
		Calendar nextDay = Calendar.getInstance();
		nextDay.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) + duration);
		nextDay.set(Calendar.HOUR, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		return nextDay.getTimeInMillis() - current.getTimeInMillis();
	}

	/**
	 * 获取下一天的间隔
	 * 
	 * @return
	 * @since fangxiang @ Dec 23, 2010
	 */
	public static final long getHourDurationAsMillis(int duration) {
		Calendar current = Calendar.getInstance();
		Calendar nextHour = Calendar.getInstance();
		nextHour.set(Calendar.HOUR, current.get(Calendar.HOUR) + duration);
		nextHour.set(Calendar.MINUTE, 0);
		nextHour.set(Calendar.SECOND, 0);
		return nextHour.getTimeInMillis() - current.getTimeInMillis();
	}

	/**
	 * 获取指定日期起始点的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @param pattern
	 *            时间格式
	 * @return 日期起始点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getDayStartAsMillis(String date, String pattern) {
		Calendar current = toCalendar(stringToDate(date, pattern));
		current.roll(Calendar.DAY_OF_MONTH, true);
		return current.getTimeInMillis();
	}

	/**
	 * 获取给定时刻(即和此刻相差多少天)的毫秒数.
	 * 
	 * @param deltaDay
	 *            多少天以前；大于0表示过去，小于0表示将来
	 * @return 毫秒数
	 * @since liushen @ Mar 9, 2011
	 */
	public static long getMillisForDeltaDay(int deltaDay) {
		long deltaMillis = deltaDay * 1000 * 60 * 60 * 24;
		return System.currentTimeMillis() - deltaMillis;
	}

	/**
	 * 将整数型的毫秒数转换成精确到分钟的时间格式
	 * 
	 * @param timeMillis
	 *            整数型的毫秒数
	 * @return 字符串类型的时间
	 */
	public static String formatMillisMin(long timeMillis) {
		return TIMEAREA_MIN_FORMAT.format(new Date(timeMillis));
	}

	/**
	 * 将整数型的毫秒数转换成默认的时间格式
	 * 
	 * @param timeMillis
	 *            整数型的毫秒数
	 * @return 字符串类型的时间
	 */
	public static String formatMillis(long timeMillis) {
		return DEFAULT_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 * @since cl @ 2010-4-28
	 */
	public static String formatTimeArea(long timeMillis) {
		return TIMEAREA_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatMillis2Date(long timeMillis) {
		return DATE_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatMillis2Time(long timeMillis) {
		return TIME_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatMillis(long timeMillis, String format) {
		return formatMillis(timeMillis, format, Locale.SIMPLIFIED_CHINESE);
	}

	/**
	 * 按指定地域的习惯来显示时间.
	 * 
	 * @since liushen @ May 25, 2011
	 */
	public static String formatMillis(long timeMillis, String format, Locale locale) {
		SimpleDateFormat sdf = (locale == null) ? new SimpleDateFormat(format) : new SimpleDateFormat(format, locale);
		return sdf.format(new Date(timeMillis));
	}

	/**
	 * 将<code>{@link DateUtil#DATE_FORMAT}</code>格式的日期字符串转换为当天0时的毫秒时间
	 * 
	 * @param date
	 *            <code>{@link DateUtil#DATE_FORMAT}</code>格式的日期字符串
	 * @return 当天0时的毫秒时间
	 * @creator changpeng @ 2009-4-30
	 */
	public static long formatDate2Millis(String date) {
		try {
			return DATE_FORMAT.parse(date).getTime();
		} catch (ParseException e) {
			LOG.error("parse date [" + date + "] fail", e);
			return 0;
		}
	}

	/**
	 * 转换日期字符串为当前小时数.
	 * 
	 * @param sDate
	 *            格式为 {@link #TIMEAREA_FORMAT}
	 * @return 整型表示的时间.
	 * @since cl @ 2010-4-28
	 */
	public static long formatTimeArea(String sDate) {
		Date date = null;
		try {
			date = TIMEAREA_FORMAT.parse(sDate);
			return date.getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 将<code>{@link DateUtil#DEFAULT_FORMAT}</code>格式的时间转换为毫秒时间
	 * 
	 * @param dateTime
	 * @return
	 * @creator changpeng @ 2009-5-14
	 */
	public static long formatDateTime(String dateTime) {
		try {
			return DEFAULT_FORMAT.parse(dateTime).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 *
	 * @param dateTime
	 * @return
	 */
	public static Date format(String dateTime) {
		try {
			return DEFAULT_FORMAT.parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String format2String(Date date) {
		return DEFAULT_FORMAT.format(date);
	}

	/**
	 * 将<code>{@link DateUtil#DEFAULT_FORMAT}</code>格式的时间转换为毫秒时间，若格式不正确返回默认值
	 * 
	 * @param dateTime
	 * @return
	 * @creator changpeng @ 2009-5-14
	 */
	public static long formatDateTime(String dateTime, long defaultValue) {
		try {
			return DEFAULT_FORMAT.parse(dateTime).getTime();
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	/**
	 * 将时间戳显示为<code>{@link DateUtil#DEFAULT_FORMAT}</code>的格式，若时间戳为负值则返回默认值.
	 * 
	 * @param timeMillis
	 *            时间戳
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @creator changpeng @ 2009-5-20
	 */
	public static String formatMillisWithDefaultValue(long timeMillis, String defaultValue) {
		return (timeMillis < 0) ? defaultValue : DEFAULT_FORMAT.format(new Date(timeMillis));
	}

	/**
	 * 将毫秒时间转换为Date类型，若小于0则返回null
	 * 
	 * @param millis
	 * @return
	 * @creator changpeng @ 2009-5-20
	 */
	public static Date millis2DateType(long millis) {
		return (millis < 0) ? null : new Date(millis);
	}

	/**
	 * 获取当前的日期时间的字符串表示
	 * 
	 * @return
	 * @creator changpeng @ 2009-6-1
	 */
	public static String getCurrentDateTime() {
		return formatMillis(System.currentTimeMillis());
	}

	/**
	 * 根据年月日构建<code>Calendar</code>对象.
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月，取值为0-11, 0表示一月.
	 * @param date
	 * @return
	 * @creator liushen @ Feb 18, 2010
	 */
	public static Calendar getCalendar(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar;
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * 
	 * @param date
	 * @return
	 * @since fangxiang @ May 31, 2010
	 */
	public static int getMondayPlus(long date) {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		cd.setTimeInMillis(date);
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		}
		return 1 - dayOfWeek;
	}

	/**
	 * 获得上周星期一的日期
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 上个星期一凌晨的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getPreviousWeekday(long date) {
		int weeks = -1;
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(date);
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		return currentDate.getTimeInMillis();
	}

	/**
	 * 获得本周一的日期
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 本周一凌晨的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getMondayOFWeek(long date) {
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(date);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		return currentDate.getTimeInMillis();
	}

	/**
	 * 上月第一天的时间
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 上月第一天的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getPreviousMonthFirst(long date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(date);
		lastDate.set(Calendar.DATE, 1); // 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1); // 减一个月，变为下月的1号
		return lastDate.getTimeInMillis();
	}

	/**
	 * 获取当月第一天的时间
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 当月第一天的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getFirstDayOfMonth(long date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(date);
		lastDate.set(Calendar.DATE, 1); // 设为当前月的1号
		return lastDate.getTimeInMillis();
	}

	/**
	 * 以默认的长格式, 显示当前时间.
	 * 
	 * @since liushen @ Dec 6, 2010
	 */
	public static String nowAsLongFormat() {
		return formatMillis(getCurrentTimeMillis(), "yyyy年MM月dd日 E");
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @since fangxiang @ Dec 23, 2010
	 */
	public static String format2String(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取今天剩余的总秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getTodayRemainAsSecond() {
		return (getNextDayBeginAsMillis() - DateUtil.getCurrentTimeMillis()) / 1000;
	}

	/**
	 * 获取本小时剩余的总秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getCurrentHourRemainAsSecond() {
		return (getNextHourBeginAsMillis() - DateUtil.getCurrentTimeMillis()) / 1000;
	}

	/**
	 * 获取明天的开始毫秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextDayBeginAsMillis() {
		return getNextDayBeginAsMillis(1);
	}

	/**
	 * 获取指定N天后的开始毫秒数
	 * 
	 * @param days
	 *            间隔天数，从1开始；
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextDayBeginAsMillis(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days); // 当前时间加一 ，然后时分秒设为0，00:00:00
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取下一个小时开始的毫秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextHourBeginAsMillis() {
		return getNextHourBeginAsMillis(1);
	}

	/**
	 * 获取下N个小时开始的毫秒数
	 * 
	 * @param hours
	 *            间隔的小时数，从1开始
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextHourBeginAsMillis(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1); // 当前时间加一 ，然后时分秒设为0，00:00:00
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取和给定日期相隔指定天数的Date对象. 大于0表示之后, 小于0表之前.
	 * 
	 * @param date
	 *            给定日期
	 * @param relativeDay
	 *            相隔天数
	 * @return
	 * @since v3.5
	 * @creator liushen @ Dec 14, 2009
	 */
	public static Date getDate(Date date, int relativeDay) {
		return getCalendar(toCalendar(date), relativeDay).getTime();
	}

	public static int month2second(int month) {
		return month * 30 * 24 * 3600;
	}

	/**
	 * 获取两个日期相差的天数，取绝对值.
	 * 
	 * @return 两个日期相差的天数。只可能为零或者正数。
	 * @see #minus(Calendar, Calendar)
	 */
	public static int betweenDays(Calendar begin, Calendar end) {
		long msBegin = begin.getTimeInMillis();
		long msEnd = end.getTimeInMillis();
		long between_days = (msEnd - msBegin) / (1000 * 3600 * 24);
		between_days = Math.abs(between_days);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取两个日期相差的天数，不取绝对值.
	 * 
	 * @param calBegin
	 *            开始日期
	 * @param calEnd
	 *            结束日期
	 * @return
	 * @since v3.5
	 * @creator liushen @ Dec 14, 2009
	 * @see #minus(Calendar, Calendar)
	 * @see #minusWithoutAbs(Date, Date)
	 */
	public static int minusWithoutAbs(Calendar calBegin, Calendar calEnd) {
		long msBegin = calBegin.getTimeInMillis();
		long msEnd = calEnd.getTimeInMillis();
		long between_days = (msEnd - msBegin) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取两个日期相差的天数，不取绝对值.
	 * 
	 * @param begin
	 * @param end
	 * @return
	 * @since v3.5
	 * @creator liushen @ Dec 14, 2009
	 */
	public static int minusWithoutAbs(Date begin, Date end) {
		long between_days = (end.getTime() - begin.getTime()) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 判断给定日期是否早于今天.
	 * 
	 * @param someDate
	 * @return
	 * @since v3.5
	 * @creator liushen @ Dec 14, 2009
	 */
	public static boolean isBeforeToday(Date someDate) {
		return minusWithoutAbs(someDate, new Date()) > 0;
	}

	/**
	 * 指定日期是否晚于今天。
	 * 
	 * @param date
	 * @return 如果晚于今天返回<code>true</code>, 否则返回<code>false</code>.
	 * @since v3.5
	 * @creator liushen @ Dec 13, 2009
	 */
	public static boolean isAfterToday(Date date) {
		return minusWithoutAbs(Calendar.getInstance(), toCalendar(date)) > 0;
	}

	/**
	 * @param date
	 * @return
	 * @since v3.5
	 * @creator liushen @ Dec 14, 2009
	 */
	public static String formatDate(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 智能解析时间；（依次按 {@link #prepareDefaultPatterns()} 返回的格式去解析)
	 * 
	 * @since liushen @ Dec 16, 2009
	 * @see #parseDate(String, List)
	 */
	public static Date parseDate(String date) {
		return parseDate(date, prepareDefaultPatterns());
	}

	/**
	 * 智能解析时间；（依次按格式去解析，返回第一个符合的)
	 * 
	 * @param date
	 *            字符串表示的时间
	 * @return
	 * @since liushen @ Dec 16, 2009
	 */
	public static Date parseDate(String date, List<String> patterns) {
		if (date == null) {
			return null;
		}

		for (Iterator<String> iterator = patterns.iterator(); iterator.hasNext();) {
			String pattern = iterator.next();
			try {
				return new SimpleDateFormat(pattern).parse(date);
			} catch (Exception e) {
			}
		}
		return null;
	}

	private static List<String> prepareDefaultPatterns() {
		List<String> patterns = new ArrayList<String>();
		patterns.add("yyyy-MM-dd");
		patterns.add("yyyy/MM/dd");
		patterns.add("yyyyMMdd");
		return patterns;
	}

	/**
	 * 
	 * 是否为yyyy.MM.dd HH格式
	 * 
	 * @param date
	 *            日期
	 * @return 是否为小时格式
	 */
	public static boolean isTimeHourFormat(String date) {
		String regex = "[0-9]+\\.[0-9]+\\.[0-9]+\\s[0-9][0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 获得与当前时间的相隔时间
	 * 
	 * @param datetime
	 *            时间
	 * @return 相隔时间
	 */
	public static String getNowBetweenDateString(String datetime) {
		if (StringUtil.isEmpty(datetime)) {
			return "";
		}
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		Calendar ca = Calendar.getInstance();
		Date dateTemp;
		try {
			dateTemp = new SimpleDateFormat(DEFAULT_TIME_PATTERN).parse(datetime);
		} catch (ParseException e) {
			return datetime;
		}
		ca.setTime(dateTemp);
		String M = ((ca.get(Calendar.MONTH) + 1) + "").length() == 1 ? "0" + (ca.get(Calendar.MONTH) + 1)
				: (ca.get(Calendar.MONTH) + 1) + "";
		String d = (ca.get(Calendar.DATE) + "").length() == 1 ? "0" + ca.get(Calendar.DATE)
				: ca.get(Calendar.DATE) + "";
		String h = (ca.get(Calendar.HOUR_OF_DAY) + "").length() == 1 ? "0" + ca.get(Calendar.HOUR_OF_DAY)
				: ca.get(Calendar.HOUR_OF_DAY) + "";
		String m = (ca.get(Calendar.MINUTE) + "").length() == 1 ? "0" + ca.get(Calendar.MINUTE)
				: ca.get(Calendar.MINUTE) + "";
		if (calendar.get(Calendar.YEAR) != ca.get(Calendar.YEAR)
				|| calendar.get(Calendar.MONTH) != ca.get(Calendar.MONTH)) {
			return ca.get(Calendar.YEAR) + "." + M + "." + d;
		} else if (calendar.get(Calendar.DATE) > ca.get(Calendar.DATE)) {
			int date = calendar.get(Calendar.DATE) - ca.get(Calendar.DATE);
			switch (date) {
			case 1:
				return "昨天 " + h + ":" + m;
			case 2:
				return "前天 " + h + ":" + m;
			default:
				return ca.get(Calendar.YEAR) + "." + M + "." + d;
			}

		} else if (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) > 1
				|| (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) == 1
						&& calendar.get(Calendar.MINUTE) > ca.get(Calendar.MINUTE))) {
			int hour = calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY);
			return hour + "小时前";
		} else if (calendar.get(Calendar.MINUTE) > ca.get(Calendar.MINUTE)
				|| (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) == 1)) {
			int hm = (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) == 1) ? 60 : 0;
			int minute = calendar.get(Calendar.MINUTE) - ca.get(Calendar.MINUTE) + hm;
			return minute + "分钟前";
		} else {
			return "刚刚";
		}
	}

	/**
	 * 增加月份
	 * 
	 * @param date
	 *            传入日期
	 * @param num
	 *            增加数量
	 * @param dateFormat
	 *            格式
	 * @return
	 * @create yz on @ 2015年2月6日下午7:12:01
	 */
	public static String addMonth(String date, int num, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(DateUtil.stringToDate(date, dateFormat));
		rightNow.add(Calendar.MONTH, num);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}


	public static HashMap<String, String> getDate(int year, int month, int day) {
		String start_time = String.valueOf(year) + "-" + String.valueOf(month) + "-01";
		String end_time = String.valueOf(year) + "-" + String.valueOf((month + 1)) + "-01";
		if (month == 12) {
			end_time = String.valueOf(year + 1) + "-01-01";
		} else if (month == -1) {// 全年（ 2015-01-01到2016-01-01）
			start_time = String.valueOf(year) + "-01-01";
			end_time = String.valueOf(year + 1) + "-01-01";
		}
		HashMap<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("start_time", start_time);
		dateMap.put("end_time", end_time);

		return dateMap;
	}

	public static boolean isAfterToday(String time, String format) {
		return isAfterToday(stringToDate(time, format));
	}

	/**
	 * 两个时间之间相差的天小时分秒
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return keys{days,hours,minutes,seconds}
	 * @author yz on @ 2016年2月17日下午5:44:30
	 */
	public static Map<String, Long> formatDuring(String beginTime, String endTime) {
		Long begin = getDateAsMillis(format(beginTime, DateUtil.DEFAULT_TIME_PATTERN));
		Long end = getDateAsMillis(format(endTime, DateUtil.DEFAULT_TIME_PATTERN));
		long mss = end - begin;
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		Map<String, Long> duringMap = new HashMap<String, Long>();
		duringMap.put("days", days);
		duringMap.put("hours", hours);
		duringMap.put("minutes", minutes);
		duringMap.put("seconds", seconds);
		return duringMap;
	}
}
