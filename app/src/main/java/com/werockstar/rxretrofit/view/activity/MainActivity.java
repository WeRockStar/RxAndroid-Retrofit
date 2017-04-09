package com.werockstar.rxretrofit.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.werockstar.rxretrofit.MainApplication;
import com.werockstar.rxretrofit.R;
import com.werockstar.rxretrofit.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MainApplication) getApplication()).getComponent().inject(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, new MainFragment())
                .commit();
    }
}
