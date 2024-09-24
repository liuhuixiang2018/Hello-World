package com.lhx.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author:95780
 * @Date: 10:03 2019/9/19
 * @Description:jdk1.8日期处理
 */
public class DateUtils {
    private static final Logger logger = LogManager.getLogger(DateUtils.class);
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DEFAULT_DATA = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String PATTERN_NO_SPLIT_CHAR = "yyyyMMddHHmmss";
    public static final String PATTERN_NO_SPLIT_CHAR_17 = "yyyyMMddHHmmssSSS";
    public static final String PATTERN_NO_SECOND = "yyyyMMddHHmm";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HHmmss";
    public static final String PATTERN_TIME_HHMM = "HHmm";
    public static final String PATTERN_DATE_NO_SPLIT_CHAR = "yyyyMMdd";

    /**
     * 转换日期类为字符串
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String parseDate(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 转换日期时间为字符串
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String parseDateTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Date转换为格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String parseDateformat(Date date, String pattern) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return parseDateTime(localDateTime, pattern);
    }


    /**
     * string类型 -> date类型
     *
     * @param timeStr
     * @return
     */
    public static Date strToDate(String timeStr) {
        return strToDate(timeStr, PATTERN_DEFAULT);
    }

    /**
     * string类型 -> date类型
     * @param timeStr
     * @param format
     * @return
     */
    public static Date strToDate(String timeStr, String format) {
        if (StringUtils.isBlank(timeStr)) {
            return null;
        }

        format = StringUtils.isBlank(format) ? PATTERN_DEFAULT : format;

        org.joda.time.format.DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.forPattern(format);
        DateTime dateTime;
        try {
            dateTime = dateTimeFormatter.parseDateTime(timeStr);
        } catch (Exception e) {
            logger.error("strToDate error: timeStr: {}", timeStr, e);
            return null;
        }
        return dateTime.toDate();
    }

    public static LocalDate strToLocalDate(String dateStr,String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, df);
    }

    public static LocalTime strToLocalTime(String timeStr, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(timeStr, df);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static Timestamp getTimestamp() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        return timestamp;
    }

    /**
     * 將Date 類型時間轉化成yyyyMMddhhmmss 格式
     *
     * @return
     */
    public static String getDateFor14(Date date) {
        SimpleDateFormat stf = new SimpleDateFormat("yyyyMMddHHmmss");
        return stf.format(date);
    }

    /**
     * 获取指定日期的前n天日期
     */
    public static String getPreDay(String  strDate, int n) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            date = simpleDateFormat.parse(strDate);
        }catch (Exception e){
            logger.error("日期转换失败");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定日期的前一天日期
     */
    public static String getPreDay(String  strDate) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            date = simpleDateFormat.parse(strDate);
        }catch (Exception e){
            logger.error("日期转换失败");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return simpleDateFormat.format(calendar.getTime());
    }
    /**
     * 获取指定日期的下一天
     */

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的下一天
     */

    public static String getNextDay(String  strDate) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            date = simpleDateFormat.parse(strDate);
        }catch (Exception e){
            logger.error("日期转换失败");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return simpleDateFormat.format(calendar.getTime());
    }
}