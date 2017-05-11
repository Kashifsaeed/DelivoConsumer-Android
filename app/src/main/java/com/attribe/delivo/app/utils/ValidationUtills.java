package com.attribe.delivo.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Uzair Qureshi
 * Date:  4/22/17.
 * Description:
 */

public class ValidationUtills
{
    public static boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPhoneNumber(String phone)
    {
        if (phone != null && phone.length() == 12) {
            return true;
        }
        return false;
    }
}
