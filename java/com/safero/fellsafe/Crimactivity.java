package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.safero.fellsafe.datastorageclasses.Recycleradapter;
import com.safero.fellsafe.datastorageclasses.Usersdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Crimactivity extends AppCompatActivity implements View.OnClickListener{


    ArrayList<Usersdata> arrayList = new ArrayList<Usersdata>();
    RecyclerView recyclerView;
    Button button;
    public static String  check="ACTION_CHECK_STATUS";

    private String Action_DO_STUFF = "dostuff";

    private ServiceConnection serviceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crimactivity);

final String[] val = getIntent().getStringExtra("location").split(" ");

button = findViewById(R.id.button4);
button.setOnClickListener(this);
recyclerView = findViewById(R.id.recycle);
        String url = "https://safetyapiforw.herokuapp.com/apifor/users/getusers";


      RequestQueue requestQueue = Volley.newRequestQueue(this);
        //
        final String number = Savednumbers.getInstance().getnumber(this);
        final String message = Savednumbers.getInstance().getmessage(this);


        JSONObject map = new JSONObject();
        try {
            map.put("location","selaqui");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Crimactivity.this,"Sent",Toast.LENGTH_LONG).show();



                try {
                    displaytouser(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Crimactivity.this,"Server error",Toast.LENGTH_LONG).show();
//
            }
        });
        requestQueue.add(stringRequest);

     //   Intent service = new Intent(getApplicationContext(),Backgroundservices.class);


    }
    public void start(){
        Backgroundservices.enqueuework(Crimactivity.this,new Intent().setAction(Action_DO_STUFF));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getExtras().getInt("status");


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            {

                NotificationChannel notificationChannel = new NotificationChannel("channelid","Myfcmnotification",NotificationManager.IMPORTANCE_HIGH);

                notificationChannel.setDescription("This is fcm notification");

                notificationManager.createNotificationChannel(notificationChannel);

            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(Crimactivity.this,"channelid")
                    .setContentTitle("Nikunj")
                    .setSmallIcon(R.drawable.ic_baseline_email_24)
                    .setContentText(status+"");




            // NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

            notificationManager.notify(199,builder.build());


        }
    };

    private void displaytouser(JSONObject response) throws JSONException {

        JSONArray jsonArray = response.getJSONArray("allusers");

        for(int i=0;i<jsonArray.length();i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String name = jsonObject.getString("name");

            String number = jsonObject.getString("number");

            String location1 = jsonObject.getString("location1");

            String profession = jsonObject.getString("profession");

            String token = jsonObject.getString("fcmtoken");

            Usersdata usersdata = new Usersdata(name,number,location1,profession,token);

            arrayList.add(usersdata);


        }

        recyclerView.setLayoutManager(new LinearLayoutManager(Crimactivity.this));

               recyclerView.setAdapter(new Recycleradapter(arrayList , Crimactivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.prof){



        }

        else if(item.getItemId() == R.id.crim){

            Intent intent = new Intent(this , Uploadcriminal.class);
            startActivity(intent);

        }

        return false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button4:

                Intent intent = new Intent(this,Allareacrim.class);
startActivity(intent);
break;

        }


    }

    @Override
    protected void onResume() {
        registerReceiver(broadcastReceiver,new IntentFilter(check));
        start();
        super.onResume();
    }
}