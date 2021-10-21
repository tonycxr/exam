package com.sungcor.exam.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String changeFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH：mm");  // 设置日期格式
        String strTime = simpleDateFormat.format(date);  // 格式转换
        return strTime;
    }

    public static String changeFormatWithoutTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  // 设置日期格式
        String strTime = simpleDateFormat.format(date);  // 格式转换
        return strTime;
    }

    public static Date minusOneDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date date1 = calendar.getTime();
        return date1;
    }

    public static void main(String[] args) {
        System.out.println(changeFormatWithoutTime(minusOneDay(new Date())));
    }
}
