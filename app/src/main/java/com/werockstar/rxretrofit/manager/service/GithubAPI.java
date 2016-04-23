package com.werockstar.rxretrofit.manager.service;

import com.werockstar.rxretrofit.model.GithubCollection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubAPI {

    @GET("users/{user}")
    Call<GithubCollection> getGithubInfo(@Path("user") String username);
}
