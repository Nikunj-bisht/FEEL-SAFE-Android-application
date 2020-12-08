package com.safero.fellsafe;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class Savednumbers {

  static Savednumbers intance = new Savednumbers();


    private Savednumbers(){

    }

    public static Savednumbers getInstance(){

        return intance;

    }


    public boolean savenumbers(Context context,String number){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
SharedPreferences.Editor editor = sharedPreferences.edit();

editor.putString("number",number);

        editor.apply();
        return true;
    }
    public boolean savemessage(Context context,String number){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("message",number);

        editor.apply();
        return true;
    }




    public String getnumber(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();


return map
        .get("number");
    }

    public void savetoken(Context context , String token){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("fcmtoken" , token);
        editor.apply();

    }

    public boolean gettoken(Context context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();

        return map.containsKey("fcmtoken") ? true : false;
    }

    public String returntoken(Context context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();

        return map.get("fcmtoken");
    }




    public String getmessage(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();


        return map
                .get("message");
    }


    public boolean checknumber(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();


        return map.containsKey("number")?true:false;
    }


}
