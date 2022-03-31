package com.example.zhabyq;

import android.app.Application;

public class VariablesForApplication extends Application{
    private static String phoneNumber, iin;

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber1) {
        phoneNumber = phoneNumber1;
    }

    public static String getIin() {
        return iin;
    }

    public static void setIin(String iin1) {
        iin = iin1;
    }
}
