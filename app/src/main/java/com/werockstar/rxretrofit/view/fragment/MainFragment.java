package com.werockstar.rxretrofit.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.werockstar.rxretrofit.MainApplication;
import com.werockstar.rxretrofit.R;
import com.werockstar.rxretrofit.manager.service.GithubAPI;
import com.werockstar.rxretrofit.model.GithubCollection;
import com.werockstar.rxretrofit.presenter.GithubPresenter;
import com.werockstar.rxretrofit.presenter.GithubPresenterImpl;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class MainFragment extends Fragment implements GithubPresenter.View {


    private ImageView imageViewAvatar;
    private TextView tvUsername;
    private TextView tvFullName;
    private EditText edtUsername;
    private Button btnSearch;

    private ProgressBar progressBar;

    private GithubPresenter presenter;

    @Inject
    GithubAPI api;


    public MainFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((MainApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        presenter = new GithubPresenterImpl(this, api);

        initialView(view);

        return view;
    }

    private void initialView(View view) {
        imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewProfile);
        tvFullName = (TextView) view.findViewById(R.id.tvFullName);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        edtUsername = (EditText) view.findViewById(R.id.edtUsername);
        progressBar.setVisibility(View.GONE);

        btnSearch.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            presenter.getGithubInfo(edtUsername.getText().toString());
        });
    }

    @Override
    public void showGithubInfo(GithubCollection collection) {
        if (collection != null) {
            tvUsername.setText(collection.getUsername());
            tvFullName.setText(collection.getFullName());

            imageViewAvatar.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(collection.getAvatar()).into(imageViewAvatar);
        }
    }

    @Override
    public void onCompleted() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Completed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.onStop();
    }
}
