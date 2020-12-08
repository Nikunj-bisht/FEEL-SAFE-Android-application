package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Uploadcriminal extends AppCompatActivity {


    Button button;

    ImageView imageView;
Button getButton;
Bitmap bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcriminal);


        button = findViewById(R.id.button6);
        imageView = findViewById(R.id.imageView2);
getButton = findViewById(R.id.button7);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(Uploadcriminal.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Uploadcriminal.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},400);
                }else{
                    selectimagefromgallary();


                }

            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = Uploadcriminal.this;
                File file = activity.getExternalFilesDir("crimphotos");
                File file1 = new File(file+"/ran.png");
                Log.d("full u=image path",file1.getAbsolutePath());
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file1);
                    bit.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    if(file1 != null){
                        Toast.makeText(Uploadcriminal.this,"File is not null ",Toast.LENGTH_SHORT).show();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void selectimagefromgallary() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,200);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 && resultCode == RESULT_OK && data!=null){

            Uri uri = data.getData();

            File file = new File(uri.getPath());
File sd = Environment.getExternalStorageDirectory();
            Log.d("Image file location" , sd+file.getAbsolutePath());
            Bitmap bitmap = null;
            try {
               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
             bit = bitmap;
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 400 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

writepermission();
            Toast.makeText(this,"Granted",Toast.LENGTH_SHORT).show();
        }else if(requestCode == 500){
            Toast.makeText(this,"Granted wwrite permi",Toast.LENGTH_SHORT).show();

        }
    }

    private void writepermission() {

        if(ActivityCompat.checkSelfPermission(Uploadcriminal.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Uploadcriminal.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},500);
        }

    }
}