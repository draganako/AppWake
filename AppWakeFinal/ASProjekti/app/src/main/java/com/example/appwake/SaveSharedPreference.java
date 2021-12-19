package com.example.appwake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import static com.example.appwake.PreferencesUtility.*;


public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn,int id,String email,int jeAdmin) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putInt("idName",id);
        editor.putString("emailName",email);

        editor.putInt("jeAdmin",jeAdmin);
        editor.putInt("status",2);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static int getLoggedStatusbudan(Context context) {
        return getPreferences(context).getInt("status", 2);
    }

    public static void setLoggedStatusbudan(Context context, int stat) {
        SharedPreferences.Editor editor = getPreferences(context).edit();

        editor.putInt("status",stat);
        editor.apply();
    }

    public  static int getLoggedId(Context context)
    {
        return getPreferences(context).getInt("idName",0);

    }
    public static String getLoggedEmail(Context context)
    {
        return getPreferences(context).getString("emailName","");

    }
    public  static int getJeAdmin(Context context)
    {
        return getPreferences(context).getInt("jeAdmin",0);

    }


}

