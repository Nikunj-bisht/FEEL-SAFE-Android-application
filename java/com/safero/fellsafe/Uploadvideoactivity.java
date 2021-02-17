package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Output;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.safero.fellsafe.requestclasses.Uservideo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Uploadvideoactivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton button;
    VideoView videoView;
    ImageButton imageButton;
    ImageButton uploadvideo;
    Handler handler;
    private FusedLocationProviderClient fusedLocationProviderClient;

    Uri uri1;
    File newfile;
    File file1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadvideoactivity);
        getSupportActionBar().hide();
        button = findViewById(R.id.button7);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        uploadvideo = findViewById(R.id.imageButton3);
        uploadvideo.setOnClickListener(this);
        videoView = findViewById(R.id.videoView2);
        button.setOnClickListener(this);
        handler = new Handler();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button7:

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 200);


                } else {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, 2000);

                }
                break;

            case R.id.imageButton:

                Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, 3000);

                break;

            case R.id.imageButton3:

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Add description");

                LayoutInflater layoutInflater = LayoutInflater.from(this);

                View view1 = layoutInflater.inflate(R.layout.videodetailsalert, null, false);
                final ProgressBar progressBar = view1.findViewById(R.id.progressBar);
                final EditText editText = view1.findViewById(R.id.editTextTextMultiLine);
                final EditText editText1 = view1.findViewById(R.id.editTextTextPersonName5);


                builder.setView(view1);


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if (ActivityCompat.checkSelfPermission(Uploadvideoactivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    //  return;
                                    //
                                    //
                                }
                                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Location> task) {
                                                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                                                            Location location = task.getResult();
                                                                                                            if (location != null) {
                                                                                                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                                                                                                                Geocoder geocoder = new Geocoder(Uploadvideoactivity.this);
                                                                                                                try {
                                                                                                                    List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                                                                                                    //  Toast.makeText(MapsActivity.this,"Permission granted ",Toast.LENGTH_LONG).show();
                                                                                                                    // latlong = location.getLatitude() + " " + location.getLongitude();
//                                                                                                                          String     loc = list.get(0).getLocality() + " " +
//                                                                                                                                     list.get(0).getSubLocality() + " " + list.get(0).getAdminArea() + " " + list.get(0).getPostalCode()
//                                                                                                                                   + " " + list.get(0).getAddressLine(0) + " " + list.get(0).getAddressLine(1) + " " + list.get(0).getCountryName();
                                                                                                                    editText1.setText(list.get(0).getLocality());
                                                                                                                    Toast.makeText(Uploadvideoactivity.this, list.get(0).getLocality(), Toast.LENGTH_LONG).show();


                                                                                                                    //    Thread.sleep(3000);
                                                                                                                } catch (Exception e) {

                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                );
                            }
                        });
                    }
                }).start();


                builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("loct", RequestBody.create(MultipartBody.FORM, editText1.getText().toString()));
                        map.put("des", RequestBody.create(MultipartBody.FORM, editText.getText().toString()));
                        //    .addFormDataPart("loct",editText1.getText().toString())
                        // .build();

                        RequestBody video = RequestBody.create(MediaType.parse(getContentResolver().getType(uri1)), newfile);

                        MultipartBody.Part multi = MultipartBody.Part.createFormData("videofile", "filer", video);

                        Retrofit.Builder builder1 = new Retrofit.Builder()
                                .baseUrl("https://safetyapiforw.herokuapp.com")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder1.build();

                        Uservideo uservideo = retrofit.create(Uservideo.class);

                        Call<ResponseBody> call = uservideo.uploadvideo(map, multi);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(Uploadvideoactivity.this, "Uploaded successfully", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                Toast.makeText(Uploadvideoactivity.this, "Not successfully", Toast.LENGTH_LONG).show();
                                t.printStackTrace();
                            }
                        });


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.show();


                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Granted permission for camera", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000 && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            uri1 = uri;
          file1 = new File(uri.getPath());
            Log.d("URI", data.getData().toString());
            //  Log.d("URI",file.toString());
            uploadvideo.setVisibility(View.VISIBLE);
            videoView.setVideoURI(uri);
            videoView.start();
        } else if (requestCode == 3000 && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            uri1 = uri;
            file1 = new File(uri.getPath());
String[] filepathcolumn = {MediaStore.Video.Media.DATA};
Cursor cursor = getContentResolver().query(uri,filepathcolumn,null,null,null);
cursor.moveToFirst();
int columnindex = cursor.getColumnIndex(filepathcolumn[0]);
String decode = cursor.getString(columnindex);
            Log.d("File location",decode+" "+file1.getAbsolutePath()+" "+file1.getName());
newfile = new File(decode);
cursor.close();

       //     Activity activity = Uploadvideoactivity.this;
            //file1 = new File(String.valueOf(activity.getExternalFilesDir("video.mp4")));
//File file = new File(file1+"/myvideo.mp4");
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//
//                OutputStream outputStream = new FileOutputStream(file);
//                byte[] bytes = new byte[1024];
//                int len;
//                while ((len = inputStream.read()) > 0) {
//                    outputStream.write(bytes, 0, len);
//                }
//                outputStream.close();
//                inputStream.close();
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //   file1 = new File(uri.getPath());

            uploadvideo.setVisibility(View.VISIBLE);

            videoView.setVideoURI(uri);
            videoView.start();

        }

    }

//    private String getpath(Uri uri) {
//
//        String[] proj = {MediaStore.Video.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
//
//
//
//}
}