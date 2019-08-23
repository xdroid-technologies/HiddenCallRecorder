package com.abc.recorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.abc.recorder.R;
import com.abc.recorder.base.FragmentContainerActivity;
import com.abc.recorder.locker.SetLockTypeActivity;

public class SettingsActivity extends PreferenceFragment {

    public static void launch(Activity from) {
        FragmentContainerActivity.launch(from, SettingsActivity.class, null);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        Preference changepasswordd = findPreference("changepassword");
        changepasswordd.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent=new Intent(getActivity(),SetLockTypeActivity.class);
                intent.putExtra("SET",true);
                startActivity(intent);
                return true;
            }
        });


        initData();
    }

    private void initData() {

    }



}
