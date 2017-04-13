package com.nailpolish.nailpolishdb;

/**
 * Created by ebelsheiser on 14.03.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import static com.nailpolish.nailpolishdb.R.id.toolbar;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        setContentView(R.layout.setting_toolbar);


        //todo: get nail polish count + set in preferences
        ViewDatabase view = new ViewDatabase();
        Preference preference = (Preference) findPreference("nailpolish_count");
        //preference.setSummary(view.countNPs());
    }
}