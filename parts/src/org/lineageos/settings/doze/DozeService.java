/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2018 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings.doze;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class DozeService extends Service {
    private static final String TAG = "DozeService";
    private static final boolean DEBUG = false;

    private AodSensor mAodSensor;

    private final BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                onDisplayOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                onDisplayOff();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) Log.d(TAG, "Creating service");

        mAodSensor = new AodSensor(this);

        final IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStateReceiver, screenStateFilter,
                Context.RECEIVER_NOT_EXPORTED);

        final PowerManager pm = getSystemService(PowerManager.class);
        if (pm != null && !pm.isInteractive()) {
            onDisplayOff();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "Destroying service");
        try {
            unregisterReceiver(mScreenStateReceiver);
        } catch (IllegalArgumentException e) {
            // Not registered
        }
        mAodSensor.destroy();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void onDisplayOn() {
        if (DEBUG) Log.d(TAG, "Display on");
        mAodSensor.disable();
    }

    private void onDisplayOff() {
        if (DEBUG) Log.d(TAG, "Display off");
        if (DozeUtils.isDozeAutoBrightnessEnabled(this) && DozeUtils.isAlwaysOnEnabled(this)) {
            mAodSensor.enable();
        }
    }
}
