package com.zhao.weather.activity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.zhao.weather.R;

import com.zhao.weather.presenter.MainPresenter;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.text_city)
    TextView textCity;
    @InjectView(R.id.text_temp)
    TextView textTemp;

    private MainPresenter mMainPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mMainPresenter = new MainPresenter(this);
        mMainPresenter.start();
    }

    public TextView getTextCity() {
        return textCity;
    }

    public TextView getTextTemp() {
        return textTemp;
    }


}
