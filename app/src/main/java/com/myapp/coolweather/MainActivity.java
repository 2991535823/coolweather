package com.myapp.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.myapp.coolweather.Activity.WeatherActivity;
import com.myapp.coolweather.json.Weather;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getString("weather",null)!=null){
            Intent intent=new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
