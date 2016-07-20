package com.werockstar.rxretrofit.di.module;

import com.werockstar.rxretrofit.manager.service.GithubAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.github.com/users/")
                .build();
    }

    @Provides
    @Singleton
    public GithubAPI provideGitHubAp(Retrofit retrofit) {
        return retrofit.create(GithubAPI.class);
    }
}
