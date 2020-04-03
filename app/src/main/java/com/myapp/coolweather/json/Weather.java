package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public NOW now;
    public Suggestion suggestion;
    @SerializedName("daily_forcast")
    public List<Forecast> forecastList;
}
