package br.edu.utfpr.rogerio.inspalarm;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static String FormatScheduleString(int hour, int minute)
    {
        if(minute < 10)
            return Integer.toString(hour) + ":0" + Integer.toString(minute);
        else
            return Integer.toString(hour) + ":" + Integer.toString(minute);
    }
}
