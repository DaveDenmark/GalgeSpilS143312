package com.example.id.galgespilaflevering.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.id.galgespilaflevering.R;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Preference extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // Get the app's shared preferences
        SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        // Get the value for the run counter
        int counter = app_preferences.getInt("counter", 0);
        int intValue = app_preferences.getInt("intValue",0);

        // Update the TextView
        TextView text = (TextView) findViewById(R.id.text);
        text.setText("Du har åbnet indstillinger "+counter+" gange.");


        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setText("Du har spillet spillet "+intValue+" gange.");

        // Increment the counter
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("counter", ++counter);
        editor.commit();

    }

}