package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public Basic basic;
    public Updata update;
    public String status;
    public NOW now;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    public AQI aqi;
    public Suggestion suggestion;
    public String msg;
}
