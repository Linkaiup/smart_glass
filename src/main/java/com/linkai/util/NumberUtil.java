package com.linkai.util;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Created by K Lin
 * on 2017/12/5.
 * at 19:38
 * description : smart_glasses
 */
@Service
public class NumberUtil {
    public boolean isInteger(String str)
    {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
