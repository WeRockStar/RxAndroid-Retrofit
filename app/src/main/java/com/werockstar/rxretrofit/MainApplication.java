package com.werockstar.rxretrofit;

import android.app.Application;

import com.werockstar.rxretrofit.di.component.AndroidComponent;
import com.werockstar.rxretrofit.di.component.DaggerAndroidComponent;
import com.werockstar.rxretrofit.di.module.ServiceModule;

public class MainApplication extends Application {

    AndroidComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAndroidComponent.builder()
                .serviceModule(new ServiceModule())
                .build();
    }

    public AndroidComponent getComponent() {
        return component;
    }
}
