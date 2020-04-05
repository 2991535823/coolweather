package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("cid")
    public String weatherCityId;
    @SerializedName("location")
    public String cityLocation;
    @SerializedName("parent_city")
    public String parentCity;
    @SerializedName("admin_area")
    public String adminArea;
    @SerializedName("cnty")
    public String country;
    @SerializedName("lat")
    public String latitude;
    @SerializedName("lon")
    public String longitude;
    @SerializedName("tz")
    public String timeZone;
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Updata update;
    public class Updata{
        @SerializedName("loc")
        public String updataTime;
        @SerializedName("utc")
        public String updataTimeutc;
    }
}
