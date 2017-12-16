package com.attribe.delivo.app.utils;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Author: Uzair Qureshi
 * Date:  7/3/17.
 * Description:
 */

public class TimeUtility {
    /*Accepted Format: yyyy-MM-dd HH:mm:ss yyyy-MM-ddTHH:mm:sszz     return dd-MMM hh:mm a     return 00-00-0000 00:00 in case of exception*/
    public static   String getStandardTime(String picktime)
    {
       // serverDate = getDataTime(serverDate);
        //serverDate.replace(" UTC","");

        Log.d("OurDate", picktime);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss");
            formatter.setTimeZone(TimeZone.getDefault());
            Date value = formatter.parse(picktime);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            picktime = dateFormatter.format(value);
            // Log.d("OurDate", OurDate);
        } catch (Exception e) {
            picktime = "";
        }
        return picktime;
    }
    public static Date addTime(String pickTime)
    {
        Log.d("OurDate", pickTime);


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
       // sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            sdf.setTimeZone(TimeZone.getDefault());
            date = sdf.parse(pickTime);
            Calendar calendarAdd = Calendar.getInstance();
            calendarAdd.setTime(date);
            calendarAdd.add(Calendar.MINUTE, 45);
            date = calendarAdd.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;

    }
    public static String getLocalTime(long timeStamp)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    public static String getLocalDate(long timeStamp)
    {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
    public static long getDateInMiliseconds(String OurDate)
    {
        //OurDate = getDataTime(OurDate);
        Log.d("OurDate", OurDate);
        Date value = null;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setTimeZone(TimeZone.getDefault());
            value = formatter.parse(OurDate);

            //Log.d("OurDate", OurDate);
        }
        catch (Exception e)
        {
            return System.currentTimeMillis();
        }
        return value.getTime();
    }
}
