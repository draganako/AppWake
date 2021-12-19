package com.example.appwake;

import android.app.Application;

public class GlobalVariables extends Application
{

    private static String profilePictureName="girl";

    public static String getprofilePictureName()
    {
        return profilePictureName;
    }

    public void setprofilePictureName(String profilePictureName)
    {
        this.profilePictureName = profilePictureName;
    }
}

