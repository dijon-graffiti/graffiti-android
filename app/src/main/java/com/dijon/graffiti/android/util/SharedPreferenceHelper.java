package com.dijon.graffiti.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

/**
 * Created by john.shelley on 10/14/15.
 */
public class SharedPreferenceHelper {

    static final String PREF_USER_ID = "user_id";

    public static SharedPreferences getSharedPreferences(Context ctx) {
        if (ctx != null) {
            if (PreferenceManager.getDefaultSharedPreferences(ctx) != null) {
                return PreferenceManager.getDefaultSharedPreferences(ctx);
            } else {
                // TODO: Throw exception eventually
                Crashlytics.log(Log.ERROR, "PREFERENCES", "Context is: " + ctx.toString());
                return PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext());
            }
        } else {
            Crashlytics.log(Log.ERROR, "PREFERENCES", "Context is: NULL");
            return null;
        }
    }

    public static String getUserId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static void setUserId(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, userName);
        editor.apply();
    }
}
