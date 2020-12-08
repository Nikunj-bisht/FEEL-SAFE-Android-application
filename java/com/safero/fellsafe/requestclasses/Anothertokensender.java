package com.safero.fellsafe.requestclasses;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.Startactivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Anothertokensender {


    public static void sendreq(final Context context , String mytoken){

        final String url = "https://fcm.googleapis.com/fcm/send";



        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject notificationdata = new JSONObject();

        JSONObject map  = new JSONObject();
        try {

            notificationdata.put("title","Hello ");
            notificationdata.put("message","Can you please help me i am here");
            map.put("to",mytoken);
            map.put("data",notificationdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context,"Sent success" , Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"Problem",Toast.LENGTH_LONG).show();

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> maps = new HashMap<>();
                maps.put("Authorization","key=AAAAWzNQBq4:APA91bFKMlMzzeEpn9HnEbiAjEylt3MPxi-fwmzB15-nbP0ROf3IZI8pFoxfeb7CLmnuLjRlvM6SAR11eKwnLkABEKjWbxz70upCvLEo9XKF7GbPJtDk9Dcl4csF7mycJyNpPvi5IxtB");
                maps.put("Content-Type","application/json");

                return maps;
            }
        };





        requestQueue.add(jsonObjectRequest);



    }




}
