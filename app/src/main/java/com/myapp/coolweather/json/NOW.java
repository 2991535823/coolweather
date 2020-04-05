package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class NOW {
    public String cloud;
    @SerializedName("cond_code")
    public String condCode;
    @SerializedName("cond_txt")
    public String condTxt;
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    @SerializedName("tmp")
    public String temperature;
    public String vis;
    @SerializedName("wind_deg")
    public String windDegree;
    @SerializedName("wind_dir")
    public String windDirection;
    public String wind_sc;
    public String wind_spd;

    @SerializedName("cond")
    public More more;
    public class More{
        public String code;
        @SerializedName("txt")
        public String info;
    }
}
