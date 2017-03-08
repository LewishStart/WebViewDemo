package com.lewish.start.webviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnNormalUse;
    private Button mBtnDownLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnNormalUse = (Button)findViewById(R.id.mBtnNormalUse);
        mBtnDownLoad = (Button)findViewById(R.id.mBtnDownLoad);

        mBtnNormalUse.setOnClickListener(this);
        mBtnDownLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtnNormalUse :
                startActivity(new Intent(this,NormalUseActivity.class));
                break;
            case R.id.mBtnDownLoad :

                break;
        }
    }
}
