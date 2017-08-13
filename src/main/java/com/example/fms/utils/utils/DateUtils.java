package com.example.fms.utils.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu on 2017/7/25.
 */
public class DateUtils {
    /**
     * 字符串转时间对象
     * @param str
     * @return
     */
    public static Date stringToDate(String str){
        if(str == null || "".equals(str))
            return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(str);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
