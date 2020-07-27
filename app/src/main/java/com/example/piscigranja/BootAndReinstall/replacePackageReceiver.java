package com.example.piscigranja.BootAndReinstall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.piscigranja.Servicios.serviceNotificacion;

public class replacePackageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(Intent.ACTION_MY_PACKAGE_REPLACED.equals(intent.getAction()))
        {
            Intent i = new Intent(context, serviceNotificacion.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startForegroundService(i);
        }

    }
}
