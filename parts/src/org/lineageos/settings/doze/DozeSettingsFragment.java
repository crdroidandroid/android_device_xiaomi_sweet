/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2024 The LineageOS Project
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

import android.os.Bundle;
import android.os.Handler;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.settingslib.widget.SettingsBasePreferenceFragment;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class DozeSettingsFragment extends SettingsBasePreferenceFragment
        implements OnPreferenceChangeListener {

    private ListPreference mDozeBrightnessPreference;

    private Handler mHandler = new Handler();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.doze_settings, rootKey);

        mDozeBrightnessPreference = (ListPreference) findPreference(DozeUtils.DOZE_BRIGHTNESS_KEY);
        mDozeBrightnessPreference.setOnPreferenceChangeListener(this);

        if (!FileUtils.isFileWritable(DozeUtils.DOZE_MODE_PATH)) {
            getPreferenceScreen().removePreference(mDozeBrightnessPreference);
        } else {
            DozeUtils.updateDozeBrightnessIcon(getContext(), mDozeBrightnessPreference);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (DozeUtils.DOZE_BRIGHTNESS_KEY.equals(preference.getKey())) {
            DozeUtils.setDozeMode((String) newValue);
        }

        mHandler.post(() -> {
            DozeUtils.checkDozeService(getActivity());
            DozeUtils.updateDozeBrightnessIcon(getContext(), mDozeBrightnessPreference);
        });

        return true;
    }
}
