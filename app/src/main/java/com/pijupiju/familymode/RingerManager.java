package com.pijupiju.familymode;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

class RingerManager {
    private final static String TAG = RingerManager.class.getSimpleName();

    static void disableRinger(Context context) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Log.d(TAG, "-> audioManager.getRingerMode: " + audioManager.getRingerMode());
    }

    static void enableRinger(Context context) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Log.d(TAG, "-> audioManager.getRingerMode: " + audioManager.getRingerMode());
    }
}
