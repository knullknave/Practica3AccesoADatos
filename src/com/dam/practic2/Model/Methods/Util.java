package com.dam.practic2.Model.Methods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util
{
    public static String formatFecha(Date fecha)
    {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return format.format(fecha);
    }

    public static Date parseFecha(String fecha)
    {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try
        {
            return format.parse(fecha);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
