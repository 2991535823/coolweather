package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String data;
    @SerializedName("cond")
    public More more;
    @SerializedName("tmp")
    public Temperature temperature;

    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
