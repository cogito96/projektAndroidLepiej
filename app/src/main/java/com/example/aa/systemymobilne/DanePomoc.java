package com.example.aa.systemymobilne;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aa on 20.01.2018.
 */

public class DanePomoc {

    private static Context context;

    public DanePomoc(Context context)
    {
        this.context = context;
    }

    public static void zapiszIlosPunktow(String s , int i)
    {
        SharedPreferences preferences = context.getSharedPreferences("PREFS" , 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(s,i);
        editor.commit();
    }

    public static int zwrocIloscPunktow (String s, int i)
    {
        SharedPreferences preferences = context.getSharedPreferences("PREFS",0);
        int punkty = preferences.getInt(s,i);
        return punkty;
    }



}
