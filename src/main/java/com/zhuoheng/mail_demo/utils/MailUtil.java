package com.zhuoheng.mail_demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Author: lizhuoheng
 * @Contact: lzh421@qq.com
 * @Date: 2020/3/7 下午 6:51
 */
public class MailUtil {

    public static boolean checkEmail(String email){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配\
        return m.matches();
    }

}
