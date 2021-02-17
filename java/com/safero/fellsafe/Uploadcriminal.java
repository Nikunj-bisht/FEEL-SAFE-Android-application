package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.safero.fellsafe.fragmentspackage.CheckandUploadcontact;
import com.safero.fellsafe.fragmentspackage.Contactfragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Uploadcriminal extends AppCompatActivity implements CheckandUploadcontact.showanotherfragment{

Button button;
String finlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcriminal);

finlocation = getIntent().getStringExtra("fine");

        Fragment fragment = new CheckandUploadcontact(this,this);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();


    }
    public void anotherfragment(){

Fragment fragment = new Contactfragment(this,finlocation);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();

    }
}