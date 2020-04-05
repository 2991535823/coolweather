package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class Updata {
    @SerializedName("loc")
    public String updataTimeloc;
    @SerializedName("utc")
    public String updataTimeutc;
}
