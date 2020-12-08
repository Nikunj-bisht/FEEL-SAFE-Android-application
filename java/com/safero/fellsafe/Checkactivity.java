package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.safero.fellsafe.requestclasses.Requestclass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Checkactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkactivity);

        final TextView textView = findViewById(R.id.textView6);


        if(!Savednumbers.getInstance().gettoken(this)){


            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull final Task<String> task) {

                    if(task.isSuccessful()){

                        textView.setText(task.getResult());
Savednumbers.getInstance().savetoken(Checkactivity.this,task.getResult());

                        Requestclass.sendrequest(Checkactivity.this
                                ,task.getResult());


                    }else{

                        textView.setText("not success");
                    }
                }
            });
        }

        else if(Savednumbers.getInstance().checknumber(Checkactivity.this)){

            try {
                Thread.sleep(2500);

                Intent intent = new Intent(this,Loadingact.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        else{

            try {
                Thread.sleep(2500);

                Intent intent = new Intent(Checkactivity.this,Startactivity.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

//        if(Savednumbers.getInstance().checkdata(this)){
//
//            new Thread(){
//
//                public void run(){
//
//                    try{
//                        Thread.sleep(3000);
//
//
//                        Intent intent = new Intent(Checkactivity.this,Loadingact.class);
//                        startActivity(intent);
//                    }catch (Exception e){
//
//                        e.printStackTrace();
//
//                    }
//
//                }
//
//            }.start();
//
//        }else {
//            new Thread(){
//
//                public void run(){
//
//                    try{
//                        Thread.sleep(3000);
//
//
//                    }catch (Exception e){
//
//                        e.printStackTrace();
//
//                    }
//
//                }
//
//            }.start();
//
//        }


    }
}