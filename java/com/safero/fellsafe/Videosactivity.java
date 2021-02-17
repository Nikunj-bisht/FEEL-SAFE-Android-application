package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.safero.fellsafe.videospackage.Videoadaptor;

import java.util.ArrayList;
import java.util.Arrays;

public class Videosactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosactivity);
//getSupportActionBar().hide();
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("https://safetyapiforw.herokuapp.com/allvideos/criminal-selakui.mp4",
        "https://safetyapiforw.herokuapp.com/allvideos/criminal-Selakui.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"));



        viewPager2.setAdapter(new Videoadaptor(Videosactivity.this,arrayList));






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.videosmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.uploadvid){

            Intent intent = new Intent(this,Uploadvideoactivity.class);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }
}