package com.intech.comptabilite.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tools
{
    public static int getYear(Date date){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        return cal.get(Calendar.YEAR);

    }
}
