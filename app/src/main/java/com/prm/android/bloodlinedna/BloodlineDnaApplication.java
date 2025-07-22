package com.prm.android.bloodlinedna;

import android.app.Application;
import android.content.Context;

public class BloodlineDnaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DnaPrincipal.loadFromStorage(this.getApplicationContext()
                .getSharedPreferences(Constants.PRINCIPAL_KEY, Context.MODE_PRIVATE)
        );
    }
}
