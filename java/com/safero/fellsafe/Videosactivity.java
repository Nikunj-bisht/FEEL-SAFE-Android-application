package com.safero.fellsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
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
getSupportActionBar().hide();
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"));



        viewPager2.setAdapter(new Videoadaptor(Videosactivity.this,arrayList));



    }
}