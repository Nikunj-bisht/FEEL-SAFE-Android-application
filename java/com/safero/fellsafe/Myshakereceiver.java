package com.safero.fellsafe;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Myshakereceiver extends BroadcastReceiver {
    Sensor sensor;
    @Override
    public void onReceive(final Context context, Intent intent) {


      SensorManager  sensorManager =(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
      sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      sensorManager.registerListener(new SensorEventListener() {
          @Override
          public void onSensorChanged(SensorEvent sensorEvent) {

       Intent intent1 = new Intent(context, MapsActivity.class);
            //  PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,0);
             //   context.startActivity(pendingIntent);
          }

          @Override
          public void onAccuracyChanged(Sensor sensor, int i) {

          }
      },sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }
}
