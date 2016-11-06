package com.soufflet.mobile.sunshine;

import android.app.Application;

import com.soufflet.mobile.sunshine.config.AdaptersModule;
import com.soufflet.mobile.sunshine.config.DaggerNetComponent;
import com.soufflet.mobile.sunshine.config.NetComponent;

public class SunshineApp extends Application {

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .adaptersModule(new AdaptersModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
