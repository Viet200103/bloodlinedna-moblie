package com.prm.android.bloodlinedna;

import okhttp3.OkHttpClient;

public class OkHttpClientProvider {
    private static OkHttpClient instance;

    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientProvider.class) {
                instance = new OkHttpClient.Builder()
                        .build();
            }
        }

        return instance;
    }

}
