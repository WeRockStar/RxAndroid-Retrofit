package com.werockstar.rxretrofit.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.werockstar.rxretrofit.R;
import com.werockstar.rxretrofit.manager.service.GithubAPI;
import com.werockstar.rxretrofit.model.GithubCollection;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainFragment extends Fragment {


    private ImageView imageViewAvatar;
    private TextView tvUsername;
    private TextView tvFullName;

    private ProgressBar progressBar;

    String BASE_URL = "https://api.github.com/users/";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initialView(view);
        getGithubInfo();

        return view;
    }

    private void getGithubInfo() {
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
                .subscribe(new Observer<GithubCollection>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getContext(), "Completed", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GithubCollection githubCollection) {
                        if (githubCollection != null) {
                            tvUsername.setText(githubCollection.getUsername());
                            tvFullName.setText(githubCollection.getFullName());

                            imageViewAvatar.setVisibility(View.VISIBLE);
                            Glide.with(getActivity()).load(githubCollection.getAvatar()).into(imageViewAvatar);
                        }
                    }
                });
    }

    private void initialView(View view) {
        imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewProfile);
        tvFullName = (TextView) view.findViewById(R.id.tvFullName);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

}
