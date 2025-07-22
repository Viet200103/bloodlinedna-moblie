package com.prm.android.bloodlinedna;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.prm.android.bloodlinedna.models.User;

public class DnaPrincipal {

    private User user;

    private static DnaPrincipal instance = null;

    private final SharedPreferences sharedPreferences;

    private DnaPrincipal(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        Gson gson = new Gson();
        String userRaw = sharedPreferences.getString(Constants.USER_KEY, null);
        if (userRaw != null) {
            user = gson.fromJson(userRaw, User.class);
        }
    }

    public synchronized static void loadFromStorage(SharedPreferences sharedPreferences) {
        instance = new DnaPrincipal(sharedPreferences);
    }

    public static DnaPrincipal getInstance() {
        return instance;
    }

    public String getToken() {
        return  sharedPreferences.getString(Constants.TOKEN_KEY, null);
    }

    public User getUser() {
        return user;
    }
}
