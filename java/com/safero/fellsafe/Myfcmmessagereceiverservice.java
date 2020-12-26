package com.safero.fellsafe;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.safero.fellsafe.broadcastreceiver.Receiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Myfcmmessagereceiverservice extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {


        super.onMessageReceived(remoteMessage);

        Log.d("message",remoteMessage.getData().toString());
        Map<String,String> map = remoteMessage.getData();

        try {
            JSONObject jsonObject = new JSONObject(map);

            String title = jsonObject.getString("title");

            shownotification(
                    jsonObject.getString("title"),jsonObject.getString("message")
            ,jsonObject.getString("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    private void shownotification(String title, String body , String num1) {


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        {


                    NotificationChannel notificationChannel = new NotificationChannel("channelid","Myfcmnotification",NotificationManager.IMPORTANCE_HIGH);

                       notificationChannel.setDescription("This is fcm notification");

                    notificationManager.createNotificationChannel(notificationChannel);

        }
        Intent intent = new Intent(this,Callactivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("number",num1);
      //  bundle.putString("name",name);

intent.putExtras(bundle);
      //  intent.putExtra("number" ,num1 );
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

//        Intent intent1 = new Intent(this, Receiver.class);
//
//        PendingIntent actionintent = PendingIntent.getBroadcast(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelid")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_baseline_email_24)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
.setContentIntent(pendingIntent)
                .setColor(Color.BLUE)
              //  .addAction(R.drawable.ic_baseline_add_ic_call_24,"Call him",actionintent)
                .setOngoing(true);




       // NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Intent intent2 = new Intent(this, Receiver.class);

        PendingIntent actionintent2 = PendingIntent.getBroadcast(this,1,intent2,0);
alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime()+10,actionintent2);


        notificationManager.notify(199,builder.build());

        //   notificationManagerCompat.notify(1000,builder.build());


    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
//        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        
        Log.d("your new token",s);

    }
}
