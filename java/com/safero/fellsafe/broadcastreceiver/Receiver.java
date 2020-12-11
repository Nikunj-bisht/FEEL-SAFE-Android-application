package com.safero.fellsafe.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "WElcome for now ll", Toast.LENGTH_SHORT).show();


    }
}
