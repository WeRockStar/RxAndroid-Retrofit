package com.werockstar.rxretrofit.di.component;


import com.werockstar.rxretrofit.di.module.ServiceModule;
import com.werockstar.rxretrofit.view.fragment.MainFragment;

import dagger.Component;

@Component(modules = ServiceModule.class)
public interface AndroidComponent {

    void inject(MainFragment fragment);
}
