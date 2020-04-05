package com.myapp.coolweather.json;

import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;

    public Sport sport;
    @SerializedName("cw")
    public CarWash carWash;

    public class Comfort{
        public String type;
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        public String type;
        public String brf;
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        public String type;
        public String brf;
        @SerializedName("txt")
        public String info;
    }
}
