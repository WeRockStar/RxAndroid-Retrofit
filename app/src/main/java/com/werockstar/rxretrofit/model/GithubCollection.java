package com.werockstar.rxretrofit.model;

import com.google.gson.annotations.SerializedName;

public class GithubCollection {

    @SerializedName("login")
    private String username;

    @SerializedName("name")
    private String fullName;

    @SerializedName("avatar_url")
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
