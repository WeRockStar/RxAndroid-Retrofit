package com.werockstar.rxretrofit.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.werockstar.rxretrofit.manager.service.GithubAPI;
import com.werockstar.rxretrofit.model.GithubCollection;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

public class HttpsManager {

    private String BASE_URL = "https://api.github.com/users/";

    private static HttpsManager httpsManager;

    public static HttpsManager getInstance() {
        if (httpsManager == null)
            httpsManager = new HttpsManager();

        return httpsManager;
    }

    private GithubAPI service;

    private HttpsManager() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(GithubAPI.class);
    }

    public GithubAPI getService() {
        return service;
    }


    public Observable<GithubCollection> getGithubInfo() {
        return HttpsManager.getInstance().getService().getGithubInfo("werockstar");
    }
}
