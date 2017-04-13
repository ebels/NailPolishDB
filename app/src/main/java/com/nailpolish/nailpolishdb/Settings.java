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
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import static com.nailpolish.nailpolishdb.R.id.toolbar;

public class Settings extends PreferenceActivity {

    private static final String TAG = Settings.class.getSimpleName();

    private AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //todo: get nail polish count + set in preferences
        ViewDatabase view = new ViewDatabase();
        Preference preference = (Preference) findPreference("nailpolish_count");
        preference.setSummary(view.countNPs());

        /*
        setContentView(R.layout.setting_toolbar);
        setSupportActionBar((Toolbar) findViewById(toolbar));
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);*/
    }

    /*
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
    */
}