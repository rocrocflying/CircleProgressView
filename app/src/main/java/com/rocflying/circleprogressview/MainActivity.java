package com.rocflying.circleprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rocflying.circleprogressview.ui.CircleProgressView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button switchColorBtn;
    private CircleProgressView circleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        switchColorBtn.setOnClickListener(this);
    }

    private void initView() {
        circleProgressView = findViewById(R.id.progess_view);
        switchColorBtn = findViewById(R.id.btn_switch_color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch_color:
                switchColor();
                break;
        }
    }

    private void switchColor() {

        circleProgressView.setValue(60);

    }
}
