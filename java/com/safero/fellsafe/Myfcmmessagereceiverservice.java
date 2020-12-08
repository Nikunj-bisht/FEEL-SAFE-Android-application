package com.safero.fellsafe;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

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
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    private void shownotification(String title, String body) {


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        {

                    NotificationChannel notificationChannel = new NotificationChannel("channelid","Myfcmnotification",NotificationManager.IMPORTANCE_HIGH);

                       notificationChannel.setDescription("This is fcm notification");

                    notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelid")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_baseline_email_24)
                .setContentText(body);




       // NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

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
