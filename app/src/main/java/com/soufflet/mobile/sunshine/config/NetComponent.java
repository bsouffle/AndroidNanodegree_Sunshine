package com.soufflet.mobile.sunshine.config;

import com.soufflet.mobile.sunshine.activities.MainActivity;
import com.soufflet.mobile.sunshine.activities.ForecastFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AdaptersModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(ForecastFragment fragment);
}
