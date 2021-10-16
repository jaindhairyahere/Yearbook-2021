package com.example.yearbook;

import android.app.Application;

import java.io.File;

import com.squareup.picasso.Picasso;


import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class YearbookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initPicasso();
        } catch (IllegalStateException ignored) {}
    }

    public void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        Cache cache = new Cache(new File(getApplicationContext().getCacheDir(), "http-cache"), 100 * 1024 * 1024);
        client.cache(cache);
        builder.downloader(new com.squareup.picasso.OkHttp3Downloader((
                client.build()
        )));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }
}
