package com.sungcor.exam.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class TimeUtil {
    public static String changeFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH：mm");  // 设置日期格式
        return simpleDateFormat.format(date);
    }

    public static String changeFormatWithoutTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  // 设置日期格式
        return simpleDateFormat.format(date);
    }

    public static Date minusOneDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
}
