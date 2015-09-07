package com.dijon.graffiti.android;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Application class that helps control singleton application functions.
 * Fabric + Crashlytics is initialized here.
 *
 * @author john.shelley
 * @since 1.0.0
 */
public class GraffitiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        // TODO: setup crash
//        if (BuildConfig.USE_CRASHLYTICS) {
//            Fabric.with(this, new Crashlytics());
//            Crashlytics.setUserEmail(SaveSharedPreference.getUserEmail(this));
//            Crashlytics.setUserName(SaveSharedPreference.getUserName(this));
//            Crashlytics.setUserIdentifier(SaveSharedPreference.getBawteUserToken(this));
//        }
    }

}
