package com.werockstar.rxretrofit.manager.service;

import com.werockstar.rxretrofit.model.GithubCollection;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubAPI {

    @GET("{user}")
    Observable<GithubCollection> getGithubInfo(@Path("user") String username);
}
