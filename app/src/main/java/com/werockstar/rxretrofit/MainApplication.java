package com.werockstar.rxretrofit;

import android.app.Application;

import com.werockstar.rxretrofit.di.component.AndroidComponent;
import com.werockstar.rxretrofit.di.component.DaggerAndroidComponent;

public class MainApplication extends Application {

    AndroidComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAndroidComponent.builder().build();
    }

    public AndroidComponent getComponent() {
        return component;
    }
}
