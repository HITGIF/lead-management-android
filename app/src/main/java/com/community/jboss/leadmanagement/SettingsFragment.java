package com.community.jboss.leadmanagement;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.community.jboss.leadmanagement.main.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preferences);

        if(getActivity()!=null) {
            final SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
            final String currentServer = sharedPref.getString(getString(R.string.saved_server_ip), "https://github.com/jboss-outreach");
            final boolean currentDarkModeState = sharedPref.getBoolean(getString(R.string.KEY_DARK_MODE), false);

            final EditTextPreference mPreference = (EditTextPreference) findPreference("server_location");
            mPreference.setSummary(currentServer);
            mPreference.setText(currentServer);

            mPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String newData = newValue.toString().trim();
                    mPreference.setSummary(newData);
                    mPreference.setText(newData);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.saved_server_ip),newData);
                    editor.apply();
                    return false;
                }
            });


            final SwitchPreferenceCompat darkModePref = (SwitchPreferenceCompat) findPreference(getString(R.string.KEY_DARK_MODE));
            darkModePref.setChecked(currentDarkModeState);
            darkModePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean newData = (boolean) newValue;
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(getString(R.string.KEY_DARK_MODE),newData);
                    editor.apply();
                    Intent intent = new Intent(getContext(), SettingsActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    getActivity().finish();
                    return false;
                }
            });
        }
    }
}
