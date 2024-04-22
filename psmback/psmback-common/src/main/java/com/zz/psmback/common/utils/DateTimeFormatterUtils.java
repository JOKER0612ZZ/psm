package com.zz.psmback.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeFormatterUtils {
    /**
     * 功能描述：将前端接受的日期格式装换位mysql能接受的格式
     *
     * @param date
     * @return {@code String }
     * @author zouzan
     * @date 2024/04/06
     */
    public static String format(String date) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
