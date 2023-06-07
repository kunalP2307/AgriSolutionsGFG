package com.example.solutiontofarming;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Utils {
    public static String utcToIST(String utcDate){
        double utc = Double.parseDouble(utcDate);
        java.util.Date time=new java.util.Date((long)utc*1000);
        int hours = time.getHours();
        int minutes = time.getMinutes();
        return hours+ ":"+minutes;
    }
}
