package com.werockstar.rxretrofit.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.werockstar.rxretrofit.R;

public class MainFragment extends Fragment {


    private ImageView imageViewAvatar;
    private TextView tvUsername;
    private TextView tvFullName;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initialView(view);

        return view;
    }

    private void initialView(View view) {
        imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewProfile);
        tvFullName = (TextView) view.findViewById(R.id.tvFullName);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
    }

}
