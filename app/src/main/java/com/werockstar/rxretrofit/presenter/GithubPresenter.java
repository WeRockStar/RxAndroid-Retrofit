package com.werockstar.rxretrofit.presenter;

import com.werockstar.rxretrofit.manager.service.GithubAPI;
import com.werockstar.rxretrofit.model.GithubCollection;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubPresenter {

    private Subscription subscribe;
    private GithubAPI api;
    private GithubPresenter.View view;

    public interface View {
        void showGithubInfo(GithubCollection collection);

        void onCompleted();

        void onError(Throwable t);
    }

    public GithubPresenter(GithubPresenter.View view, GithubAPI api) {
        this.view = view;
        this.api = api;
    }

    public void getGithubInfo(String username) {
        subscribe = api.getGithubInfo(username)
                .map(info -> {
                    info.setFullName("Full name : " + info.getFullName());
                    info.setUsername("Username : " + info.getUsername());
                    return info;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(
                        github -> view.showGithubInfo(github),
                        throwable -> view.onError(throwable),
                        () -> view.onCompleted()
                );
    }

    public void onStop() {
        if (subscribe != null && !subscribe.isUnsubscribed())
            subscribe.unsubscribe();
    }

}
