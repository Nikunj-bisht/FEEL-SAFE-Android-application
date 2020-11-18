package com.safero.fellsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Checkactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkactivity);


        if(Savednumbers.getInstance().checkdata(this)){

            new Thread(){

                public void run(){

                    try{
                        Thread.sleep(3000);


                        Intent intent = new Intent(Checkactivity.this,Loadingact.class);
                        startActivity(intent);
                    }catch (Exception e){

                        e.printStackTrace();

                    }

                }

            }.start();

        }else {
            new Thread(){

                public void run(){

                    try{
                        Thread.sleep(3000);


                        Intent intent = new Intent(Checkactivity.this,Startactivity.class);
                        startActivity(intent);
                    }catch (Exception e){

                        e.printStackTrace();

                    }

                }

            }.start();

        }


    }
}