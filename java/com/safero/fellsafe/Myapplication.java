package com.safero.fellsafe;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class Myapplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {

        MultiDex.install(this);

        super.attachBaseContext(base);

    }

}
