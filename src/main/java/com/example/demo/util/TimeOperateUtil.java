package com.example.demo.util;

import java.time.LocalTime;


/**
 * @Description: 时间工具类
 */
public class TimeOperateUtil {

    /**
     * 判断当前时间是否大于指定时间
     * @param targetTime
     * @return 当前时间大于指定时间 返回true
     */
    public static boolean currentTimeIsAfter(LocalTime targetTime) {
        LocalTime currentTime = LocalTime.now();
        // 判断当前时间是否大于指定时间
        if (currentTime.isAfter(targetTime)) {
            return true;  //当前时间大于指定时间
        } else {
            return false;
        }
    }

}
