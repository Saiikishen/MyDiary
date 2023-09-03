package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Getmain {
    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
