package com.example.groupb.phonemodechangerapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button setSilentBtn, resetBtn;
    AudioManager audioManager;
    NotificationManager notificationManager;
    int userRingerMode;
    int preference;

    private static final int RINGER_MODE_SILENT = 0;
    private static final int RINGER_MODE_VIBRATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        userRingerMode = audioManager.getRingerMode();
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        preference = 0;
        initViews();
    }

    private void initViews() {
        setSilentBtn = findViewById(R.id.setSilentBtn);
        resetBtn = findViewById(R.id.resetBtn);
        setSilentBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.setSilentBtn:
                if(userRingerMode != RINGER_MODE_SILENT || userRingerMode != RINGER_MODE_VIBRATE){
                    switch (preference) {
                        case 0:  audioManager.setMode(RINGER_MODE_SILENT);
                        break;
                        case 1:  audioManager.setMode(RINGER_MODE_VIBRATE); break;
                        default: audioManager.setMode(RINGER_MODE_SILENT); break;
                    }

                }
                break;
            case R.id.resetBtn:
                audioManager.setMode(userRingerMode);
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        //Check if the phone is running Marshmallow or above
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            //If the permission is not granted, launch an inbuilt activity to grant permission
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
            }
        }
    }

}