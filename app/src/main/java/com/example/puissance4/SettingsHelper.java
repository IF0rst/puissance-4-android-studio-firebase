package com.example.puissance4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.view.View;
import androidx.core.content.ContextCompat;

public class SettingsHelper {
    private static final String PREF_NAME = "MyPreferences";
    private SharedPreferences sharedPreferences;
    private Context context;

    public SettingsHelper(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void ApplyOptions(View rootView) {
        //Dark mode
        boolean isDarkMode = getBoolean("DarkMode", false);
        int backgroundColor = isDarkMode ? android.R.color.black : android.R.color.white;
        rootView.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));

        if (rootView instanceof View) {
            if (rootView instanceof android.widget.Button || rootView instanceof android.widget.ImageView) {
                int tintColor = isDarkMode ? android.R.color.white : android.R.color.black;
                rootView.getBackground().setColorFilter(ContextCompat.getColor(context, tintColor), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
