package com.example.piscigranja;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BootCompletedReceiver extends BroadcastReceiver {

    private FirebaseAuth mAuth;
    @Override
    public void onReceive(Context context, Intent intent) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            if (mAuth.getCurrentUser() != null && user.isEmailVerified())
            {
            Intent i = new Intent(context,serviceNotificacion.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startForegroundService(i);

            }

        }

    }

}