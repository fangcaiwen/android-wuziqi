package com.study.wind.wuziqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WuziqiView mWuziqiView;

    private Button mButton1;

    private Button mButton2;

    private TextView mRemarkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWuziqiView = (WuziqiView)findViewById(R.id.mWuziqiView);
        mButton1 = (Button)findViewById(R.id.mButton1);
        mButton2 = (Button)findViewById(R.id.mButton2);
        mRemarkText = (TextView)findViewById(R.id.mRemarkText);

        mWuziqiView.setTextView(mRemarkText);
        init();
    }

    // 初始化界面
    public void init(){
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWuziqiView.reStart();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWuziqiView.backStep();
            }
        });
    }

}
