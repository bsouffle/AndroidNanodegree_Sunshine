package com.soufflet.mobile.sunshine.config;

import com.soufflet.mobile.sunshine.adapter.weather.OpenWeatherMapAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AdaptersModule {

    @Provides
    @Singleton
    OpenWeatherMapAdapter getOpenWeatherMapAdapter() {
        return new OpenWeatherMapAdapter();
    }
}
