package com.safero.fellsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
private LocationRequest locationRequest;
    LocationManager locationManager;
SmsManager smsManager;
    PendingIntent pendingIntent;

    SensorManager sensorManager;
    float mlast;
    float mcurrent;
    int counter=0;
    Myshakereceiver myshakereceiver;
    Bitmap b ;
     String message;
     String loc;

     Button bt;
     String latlong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
message= Savednumbers.getInstance().getnumber(this);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),MapsActivity.class),0);
        mapFragment.getMapAsync(this);
    //
        bt = findViewById(R.id.button3);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callnextact = new Intent(MapsActivity.this,Crimactivity.class);
//    call.setData(Uri.parse("tel:"+message));
                callnextact.putExtra("location",loc);
                startActivity(callnextact);
            }
        });
          sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
mlast = SensorManager.GRAVITY_EARTH;
mcurrent = SensorManager.GRAVITY_EARTH;

        myshakereceiver = new Myshakereceiver();
        locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        preparelocation();

     //   permissionforlocation();
    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
//
            float x = sensorEvent.values[0];
//
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            mlast=mcurrent;
          mcurrent=(float)Math.sqrt((double) (x*x+y*y+z*z));
            float del = mcurrent-mlast;
            if(del>12) {
//                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//                startActivity(intent);
                Toast.makeText(MapsActivity.this,"Device Shaked",Toast.LENGTH_SHORT).show();
counter++;
if(counter>4){
//    counter=0;



}
            }
            }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
      permissionforlocation();
        //  showtheusercurrentlocation();
    }

    private void permissionforlocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }





        else {
    smsManager = SmsManager.getDefault();

            showtheusercurrentlocation();

        }


    }

    private void showtheusercurrentlocation() {


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);


            } else {
mMap.clear();
                if(locationRequest == null){

                    locationRequest = LocationRequest.create();

                    if(locationRequest!=null){
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationRequest.setInterval(5000);
                        locationRequest.setFastestInterval(1000);
                        locationRequest.setSmallestDisplacement(10);
                        LocationCallback locationCallback = new LocationCallback(){

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                               // super.onLocationResult(locationResult);

                                showtheusercurrentlocation();


                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());
                    }




                }

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        Location location = task.getResult();
                        if (location != null) {

                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            Geocoder geocoder = new Geocoder(MapsActivity.this);
                            try {
                                List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                              //  Toast.makeText(MapsActivity.this,"Permission granted ",Toast.LENGTH_LONG).show();
                                latlong=location.getLatitude()+" "+location.getLongitude();
 loc=list.get(0).getLocality()+" "+
        list.get(0).getSubLocality()+" "+list.get(0).getAdminArea()+" "+list.get(0).getPostalCode()
        +" "+list.get(0).getAddressLine(0)+" "+list.get(0).getAddressLine(1)+" "+list.get(0).getCountryName();
                            //    new Sendingclass(MapsActivity.this,loc).start();

                              //  smsManager.sendTextMessage("8920027160",null, list.get(0).getLocality(),null,null);

                                //new Sendingclass(list.get(0),smsManager).start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            mMap.addMarker(new MarkerOptions().position(latLng).title("Your current location"));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f);
                            mMap.moveCamera(cameraUpdate);
                       MarkerOptions markerOptions = new MarkerOptions();
                       markerOptions.position(latLng);
                       markerOptions.title("Sending this location");
                       mMap.addMarker(markerOptions);

                            CircleOptions circleOptions = new CircleOptions();
                            circleOptions.center(latLng);
                            circleOptions.radius(300);
                            circleOptions.strokeWidth(20.0f);
                            circleOptions.strokeColor(Color.RED);
                            mMap.addCircle(circleOptions);

                        } else {

                        }

                    }
                });
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("GPS");
            builder.setMessage("This is to notify that your location is disabled click Yes to enable it");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

//showtheusercurrentlocation();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    showtheusercurrentlocation();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void preparelocation(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            // showtheusercurrentlocation();
askforsms();
        } else if (requestCode == 4000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Ready to send messages", Toast.LENGTH_LONG).show();
            //smsManager = SmsManager.getDefault();
            // showtheusercurrentlocation();
            askforcall();


        } else if (requestCode == 6000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Ready to make calls", Toast.LENGTH_LONG).show();
            //  smsManager = SmsManager.getDefault();
            showtheusercurrentlocation();


        }
    }

    private void askforcall() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 6000);


        }else {
            showtheusercurrentlocation();
        }
    }

    private void askforsms() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 4000);


        }else {
            askforcall();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
     //   Toast.makeText(this,"Resumed",Toast.LENGTH_LONG).show();
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
       // permissionforlocation();
      //         showtheusercurrentlocation();
        }
       // sensorManager.unregisterListener(sensorEventListener);
//unregisterReceiver(myshakereceiver);
    }

    @Override
    protected void onPause() {
       // sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
       sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
        IntentFilter intentFilter = new IntentFilter();

//registerReceiver(myshakereceiver,intentFilter);
   //     Toast.makeText(this,"Paused",Toast.LENGTH_LONG).show();

    }
}