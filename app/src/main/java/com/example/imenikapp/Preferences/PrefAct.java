package com.example.imenikapp.Preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.imenikapp.R;

public class PrefAct extends android.preference.PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFrag()).commit();

    }

    public static class PrefFrag extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preference_activity);
        }
    }
}


