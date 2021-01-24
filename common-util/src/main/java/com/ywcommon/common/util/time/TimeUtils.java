package com.ywcommon.common.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TimeUtils {

    /**
     * 获取时间格式yyyy-MM-dd HH:mm:ss
     * @param timeStamp
     * @return
     */
    public static String parseTimeStampYmdhms(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timeStamp));
    }

    /**
     * 获取时间格式yyyy-MM-dd HH:mm
     * @param timeStamp
     * @return
     */
    public static String parseTimeStampYmdhm(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(timeStamp));
    }

    /**
     * 获取自定义时间格式
     * @param timeStamp
     * @param pattern
     * @return
     */
    public static String parseTimeStamp(long timeStamp,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date(timeStamp));
    }


    /**
     * yyyy-MM-dd时间字符串转化为日期
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date getDateTimeYMD(String dateString) throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(dateString);
    }

    /**
     * 指定时间字符串转化为日期
     * @param dateString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date getDateTime(String dateString, String pattern) throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat(pattern, Locale.getDefault())
                .parse(dateString);
    }

}
