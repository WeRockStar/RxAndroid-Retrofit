package com.werockstar.rxretrofit.presenter;

import com.werockstar.rxretrofit.manager.HttpsManager;
import com.werockstar.rxretrofit.model.GithubCollection;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubPresenterImpl implements GithubPresenter {

    private GithubPresenter.View view;

    public GithubPresenterImpl(View view) {
        this.view = view;
    }

    @Override
    public void getGithubInfo() {
        HttpsManager.getInstance().getGithubInfo()
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

}
