package com.example.weatherapplication.utils;

import android.util.Log;

public class Utils {

    private static final String TAG = "MyTag: Util";

    public static boolean moreThanOne(String string){
        boolean b = false;
        String[] size = string.split(" ");
        Log.d(TAG, "moreThanOne: size "+size.length);
        if(size.length>1){
            b = true;
        }
        return b;
    }

    public static boolean onlyOne(String string){
        boolean b = false;
        String[] size = string.split(" ");
        Log.d(TAG, "moreThanOne: size "+size.length);
        if(size.length==1){
            b = true;
            Log.d(TAG, "onlyOne: only one");
        }
        return b;
    }

    public static String makeCapital(String string){
        String str = string;
        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        if(moreThanOne(cap)){
            String[] size = cap.split(" ");
            cap = size[0]+"\n"+size[1];
        }
        //Log.d(TAG, "make\nCapital: "+cap);
        return cap;
    }
    public static String makeCapital(String string,int x){
        String str = string;
        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        if(moreThanOne(cap)){
            String[] size = cap.split(" ");
            cap = size[0]+"\n"+size[1];
        }
        //Log.d(TAG, "make\nCapital: "+cap);
        return cap;
    }
}
