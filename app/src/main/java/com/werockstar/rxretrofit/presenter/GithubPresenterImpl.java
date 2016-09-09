package com.werockstar.rxretrofit.presenter;

import com.werockstar.rxretrofit.manager.HttpsManager;
import com.werockstar.rxretrofit.manager.service.GithubAPI;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubPresenterImpl implements GithubPresenter {

    private GithubPresenter.View view;
    private Subscription subscribe;
    private GithubAPI api;

    public GithubPresenterImpl(View view, GithubAPI api) {
        this.view = view;
        this.api = api;
    }

    @Override
    public void getGithubInfo(String username) {
        subscribe = api.getGithubInfo(username)
                .map(info -> {
                    info.setFullName("Full name : " + info.getFullName());
                    info.setUsername("Username : " + info.getUsername());
                    return info;
                })
                .onErrorResumeNext(throwable -> Observable.empty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(
                        github -> view.showGithubInfo(github),
                        throwable -> view.onError(throwable),
                        () -> view.onCompleted()
                );
    }

    @Override
    public void onStop() {
        if (!subscribe.isUnsubscribed())
            subscribe.unsubscribe();
    }

}
