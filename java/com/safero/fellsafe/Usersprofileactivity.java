package com.safero.fellsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.datastorageclasses.Usersdata;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Usersprofileactivity extends AppCompatActivity {


    public static String  check="ACTION_CHECK_STATUS";

    private String Action_DO_STUFF = "dostuff";
    String location;
    String receivertoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersprofileactivity);


        Usersdata usersdata = (Usersdata) getIntent().getSerializableExtra("user");
        receivertoken = usersdata.getToken();

location = getIntent().getStringExtra("loca");


        TextView textView = findViewById(R.id.textView23);
        TextView textView1 = findViewById(R.id.textView24);
TextView textView2 = findViewById(R.id.textView25);

textView2.setText(usersdata.getName());


        textView.setText(usersdata.getLocation1());
        textView1.setText(usersdata.getNumber());

        ImageButton imageButton = findViewById(R.id.imageButton6);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start();

            }
        });


    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String url = "https://fcm.googleapis.com/fcm/send";



            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JSONObject notificationdata = new JSONObject();

            JSONObject map  = new JSONObject();
            try {

                notificationdata.put("title","Its an emergency help me i am here");
                notificationdata.put("message",location);
                notificationdata.put("number", Savednumbers.getInstance().getnumber(context));
                notificationdata.put("type","conti");
                map.put("to",receivertoken);
                map.put("data",notificationdata);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(context,"Sent success" , Toast.LENGTH_SHORT).show();
//fort.callstart();
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
    };

    public void start(){
        Backgroundservices.enqueuework(this,new Intent().setAction(Action_DO_STUFF));
    }
    @Override
    protected void onStop() {



        super.onStop();


    }

    @Override
    protected void onResume() {
        registerReceiver(broadcastReceiver,new IntentFilter(check));

        super.onResume();
    }
}

//    int status = intent.getExtras().getInt("status");
//
//
//    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//            {
//
//                    NotificationChannel notificationChannel = new NotificationChannel("channelid2","Myfcmnotification2",NotificationManager.IMPORTANCE_HIGH);
//
//                    notificationChannel.setDescription("This is fcm notification");
//
//                    notificationManager.createNotificationChannel(notificationChannel);
//
//                    }
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Usersprofileactivity.this,"channelid2")
//                    .setContentTitle("Dont worry")
//                    .setSmallIcon(R.drawable.ic_baseline_email_24)
//                    .setContentText("The notifiation has been received by "+status+" users!");
//
//
//
//
//                    // NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
//
//                    notificationManager.notify(199,builder.build());
//
