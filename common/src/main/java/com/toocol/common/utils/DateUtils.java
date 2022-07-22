package com.toocol.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

/**
 * the util class of date operation
 *
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/22 15:48
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * Get current date of type Date.
     *
     * @return Date() current date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Get current date of type String, the default pattern is yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get date of pattern in path, year/month/day, such as 2022/07/22
     */
    public static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * Get date of pattern in path, such as 20220722
     */
    public static String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * Transfer the string of date to Date
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Get the server start time
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * Calculate the time diff between two dates in String
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // get the millisecond diff between two dates
        long diff = endDate.getTime() - nowDate.getTime();
        // calculate day
        long day = diff / nd;
        // calculate hour
        long hour = diff % nd / nh;
        // calculate minute
        long min = diff % nd % nh / nm;
        // calculate second
        // long sec = diff % nd % nh % nm / ns;
        return day + "d" + hour + "h" + min + "m";
    }

    /**
     * Get yesterday date of zero clock
     */
    public static Date getLastDateAtZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get today date of zero clock
     */
    public static Date getCurrentDateAtZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Calculate days between two dates
     */
    public static int diffDays(Date begin, Date end) {
        return (int) ((end.getTime() - begin.getTime()) / (1000 * 24 * 3600));
    }

    /**
     * Calculate days between two dates
     */
    public static int diffDays(String beginStr, String endStr) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            Date begin = format.parse(beginStr);
            Date end = format.parse(endStr);
            return (int) ((end.getTime() - begin.getTime()) / (1000 * 24 * 3600));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Calculate days between two dates
     */
    public static int diffDays(String beginStr, String endStr, String datePattern) {
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        try {
            Date begin = format.parse(beginStr);
            Date end = format.parse(endStr);
            return (int) ((end.getTime() - begin.getTime()) / (1000 * 24 * 3600));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Transfer timestamp to date string
     */
    public static String timestampToStrDate(long millisecond, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(millisecond));
    }

    /**
     * Get the date of last week on Monday
     */
    public static Date getLastMonday() {
        Calendar calendar = getInstance();
        calendar.set(DAY_OF_WEEK, 2);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        calendar.add(WEEK_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * Get the zero time of the first day of last month
     */
    public static Date getLastMonthDay() {
        Calendar calendar = getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get the zero time of Monday this week
     */
    public static Date getCurrentMonday() {
        Calendar calendar = getInstance();
        calendar.set(DAY_OF_WEEK, 2);
        calendar.set(HOUR, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get Monday zero time of the previous weeks
     */
    public static Date getLastWeeksMonday(int lastWeeks) {
        Calendar calendar = getInstance();
        calendar.set(DAY_OF_WEEK, 2);
        calendar.set(HOUR, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        calendar.add(WEEK_OF_YEAR, -1 * lastWeeks);
        return calendar.getTime();
    }

    /**
     * Get the zero time before the specified time and the specified number of days
     */
    public static Date getLastDaysDate(Date target, int lastDays) {
        Calendar calendar = getInstance();
        calendar.setTime(target);
        calendar.set(HOUR, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        calendar.add(DAY_OF_YEAR, -1 * lastDays);
        return calendar.getTime();
    }

    /**
     * Get the time of zero point of the next day at the specified time
     */
    public static Date getNextDayZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        calendar.add(DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}
