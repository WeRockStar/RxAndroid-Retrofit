package com.werockstar.rxretrofit.presenter;

import com.werockstar.rxretrofit.model.GithubCollection;

public interface GithubPresenter {

    void getGithubInfo();

    interface View {
        void showGithubInfo(GithubCollection collection);

        void onCompleted();

        void onError(Throwable t);
    }
}
