package com.safero.fellsafe;

import android.content.Context;
import android.location.Address;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Sendingclass extends Thread {

    String string = "8920027160";
   // Address address;
    Context context;
String loc;
    public Sendingclass(Context context,String loc){
       // this.address = address;
       // this.smsManager = smsManager;
this.context = context;
    this.loc = loc;
    }

    public void run(){
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String url = "https://safetyapiforw.herokuapp.com/accept";

            final String number = Savednumbers.getInstance().getnumber(context);
            final String message = Savednumbers.getInstance().getmessage(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context,"Sent",Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(context,"Server error",Toast.LENGTH_LONG).show();

                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    HashMap<String,String> map = new HashMap<>();
                    map.put("num",number);
                    map.put("message",message+" He are in "+loc);
                            return map;
                }
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<>();
map.put("Content-Type","application/x-www-form-urlencoded");
return map;
                }
            };
requestQueue.add(stringRequest);
            // smsManager.sendTextMessage(string, null, address.getLocality(), null, null);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context,"Unable",Toast.LENGTH_LONG).show();
        }

    }



}
