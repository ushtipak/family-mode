package com.pijupiju.familymode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("VANCOOOOVER", "Received action: " + intent.getAction());
        String action = intent.getAction();
        assert action != null;
        if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {

            Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(i);
        }
    }

}
