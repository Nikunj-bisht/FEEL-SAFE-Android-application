package com.safero.fellsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Loadingact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingact);


        new Thread(){

            public void run(){

                try{
                    Thread.sleep(6000);


                    Intent intent = new Intent(Loadingact.this,MapsActivity.class);
                    startActivity(intent);
                }catch (Exception e){

                    e.printStackTrace();

                }

            }

        }.start();

    }
}