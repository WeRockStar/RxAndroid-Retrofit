package com.werockstar.rxretrofit.presenter;

import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.werockstar.rxretrofit.manager.service.GithubAPI;
import com.werockstar.rxretrofit.model.GithubCollection;
import com.werockstar.rxretrofit.view.fragment.MainFragment;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubPresenterImpl implements GithubPresenter {

    private GithubPresenter.View view;
    String BASE_URL = "https://api.github.com/users/";

    public GithubPresenterImpl(View view) {
        this.view = view;
    }

    @Override
    public void getGithubInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GithubAPI api = retrofit.create(GithubAPI.class);
        Observable<GithubCollection> observable = api.getGithubInfo("werockstar");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new SubscribeGithubInfo());
    }

    public class SubscribeGithubInfo implements Observer<GithubCollection> {

        @Override
        public void onCompleted() {
            view.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            view.onError(e);
        }

        @Override
        public void onNext(GithubCollection collection) {
            view.showGithubInfo(collection);
        }
    }
}
