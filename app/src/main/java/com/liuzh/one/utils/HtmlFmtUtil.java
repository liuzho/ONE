package com.liuzh.one.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 刘晓彬 on 2017/3/24.
 */

public class HtmlFmtUtil {

    public static String fmt(String str) {
        //用正则将img的style置为空
        Pattern pattern = Pattern.compile("style=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");

        return "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>Sign in | Score System</title>" +
                "<style type=\"text/css\">\n" +
                "img{margin-top:15px;margin-bottom:15px;}" +
                "body{display:flex;flex-direction:column;justify-content:center;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                str +
                "</body>" +
                "</html>";
    }
}
