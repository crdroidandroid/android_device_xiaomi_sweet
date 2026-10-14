/*
 * Copyright (C) 2021 The LineageOS Project
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

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import org.lineageos.settings.sensors.SensorsUtils;

public class AodSensor implements SensorEventListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "AodSensor";

    private static final String SENSOR_TYPE_AOD = "xiaomi.sensor.aod";

    private static final float AOD_LIGHT_LOW = 3f;
    private static final float AOD_LIGHT_HIGH = 4f;
    private static final float AOD_LIGHT_DARK = 5f;

    private final SensorManager mSensorManager;
    private final Sensor mSensor;
    private final HandlerThread mHandlerThread;
    private final Handler mHandler;

    private boolean mEnabled;
    private String mLastMode;

    public AodSensor(Context context) {
        mSensorManager = context.getSystemService(SensorManager.class);
        mSensor = mSensorManager != null
                ? SensorsUtils.getSensor(mSensorManager, SENSOR_TYPE_AOD) : null;
        if (mSensor == null) {
            Log.w(TAG, "AOD sensor not found, auto doze brightness unavailable");
        }

        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float value = event.values[0];
        if (DEBUG) Log.d(TAG, "Got sensor event: " + value);

        final String mode;
        if (value == AOD_LIGHT_LOW || value == AOD_LIGHT_DARK) {
            mode = DozeUtils.DOZE_MODE_LBM;
        } else if (value == AOD_LIGHT_HIGH) {
            mode = DozeUtils.DOZE_MODE_HBM;
        } else {
            return;
        }

        if (mode.equals(mLastMode)) {
            return;
        }
        if (DozeUtils.setDozeMode(mode)) {
            mLastMode = mode;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /* Empty */
    }

    protected void enable() {
        mHandler.post(() -> {
            if (mEnabled || mSensor == null) {
                return;
            }
            if (DEBUG) Log.d(TAG, "Enabling");
            mLastMode = null;
            mEnabled = mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL, mHandler);
        });
    }

    protected void disable() {
        mHandler.post(this::disableInternal);
    }

    protected void destroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.post(this::disableInternal);
        mHandlerThread.quitSafely();
    }

    private void disableInternal() {
        if (!mEnabled) {
            return;
        }
        if (DEBUG) Log.d(TAG, "Disabling");
        mSensorManager.unregisterListener(this, mSensor);
        mEnabled = false;
    }
}
