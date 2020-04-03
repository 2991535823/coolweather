package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class NOW {
    @SerializedName("tem")
    public String temperature;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
