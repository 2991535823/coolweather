package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Updata updata;
    public class Updata{
        @SerializedName("loc")
        public String updataTime;
    }
}
