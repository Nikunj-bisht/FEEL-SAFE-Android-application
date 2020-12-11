package com.safero.fellsafe;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class Backgroundservices extends JobIntentService  {

    Context context;
int i=0;

    public static void enqueuework(Context context , Intent intent)
    {


        enqueueWork(context , Backgroundservices.class , 101 ,intent);


    }

         @Override
    protected void onHandleWork(@NonNull Intent intent) {


//    Toast.makeText(context,"Hello "+i,Toast.LENGTH_SHORT).show();
 Bundle bundle = new Bundle();
    bundle.putInt("status",i);
    sendBroadcast(new Intent().setAction(Crimactivity.check).putExtras(bundle)); // It will send the data to the broadcast receiver which is in  Mainactivity
i++;
}





    @Override
    public boolean onStopCurrentWork() {

        return super.onStopCurrentWork();

    }
}
