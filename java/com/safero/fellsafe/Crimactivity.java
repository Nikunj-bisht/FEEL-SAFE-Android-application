package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.safero.fellsafe.requestclasses.Anothertokensender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Crimactivity extends AppCompatActivity implements View.OnClickListener , Recycleradapter.foretell{


    ArrayList<Usersdata> arrayList = new ArrayList<Usersdata>();
    RecyclerView recyclerView;
    Button button;
    String[] val;
    ImageButton imageButton;
Button butt;
String fulllocation;

    private ServiceConnection serviceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crimactivity);

val = getIntent().getStringExtra("location").split(" ");


imageButton = findViewById(R.id.imageButton2);
imageButton.setOnClickListener(this);
button = findViewById(R.id.button4);
butt = findViewById(R.id.button9);
butt.setOnClickListener(this);
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
        findViewById(R.id.finder).setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(Crimactivity.this));
       fulllocation =  Arrays.toString(val);
               recyclerView.setAdapter(new Recycleradapter(arrayList , Crimactivity.this , Crimactivity.this ,fulllocation));

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

   intent.putExtra("fine",fulllocation);

            startActivity(intent);

        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button4:

                Intent intent = new Intent(this,Allareacrim.class);
startActivity(intent);
break;

            case R.id.imageButton2:


                Intent intent1 = new Intent(this,Videosactivity.class);
                startActivity(intent1);
break;

            case R.id.button9:
                Executor executor = Executors.newSingleThreadExecutor();


                BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)    // Biometric just for safety purposes
                        .setTitle("Please verify first")
                        .setSubtitle("Go fast")
                        .setNegativeButton("NO", executor, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                            }
                        }).build();

                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        for(int i=0;i<arrayList.size();i++){

                            Anothertokensender crimactivity = new Anothertokensender(getApplicationContext(),arrayList.get(i).getToken(),"kl");
                            crimactivity.start();

                        }
                    }
                });



                break;
        }


    }

    @Override
    protected void onResume() {
     //   registerReceiver(broadcastReceiver,new IntentFilter(check));
     //   start();
        super.onResume();
    }

    @Override
    public void callstart(int i,String location) {

Intent intent = new Intent(this,Usersprofileactivity.class);

intent.putExtra("user",arrayList.get(i));
intent.putExtra("loca",location);

startActivity(intent);

    }
}