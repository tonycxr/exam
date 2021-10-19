package com.sungcor.exam.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String changeFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // 设置日期格式
        String strTime = simpleDateFormat.format(date);  // 格式转换
        return strTime;
    }
}
