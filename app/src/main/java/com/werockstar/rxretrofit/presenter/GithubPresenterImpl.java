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

    public GithubPresenterImpl(View view) {
        this.view = view;
    }

    @Override
    public void getGithubInfo() {
        subscribe = HttpsManager.getInstance().getGithubInfo()
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
                        githubCollection -> view.showGithubInfo(githubCollection),
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
