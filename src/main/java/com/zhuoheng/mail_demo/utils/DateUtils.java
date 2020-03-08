package com.zhuoheng.mail_demo.utils;

import java.util.Date;
/**
 * @Author: lizhuoheng
 * @Contact: lzh421@qq.com
 * @Date: 2020/3/7 下午 6:51
 */
public class DateUtils {

    public static Date addMinutes(Date orderTime, int orderTimeout) {
        Date afterDate = new Date(orderTime.getTime() + 60000 * orderTimeout);
        return afterDate;
    }

}
