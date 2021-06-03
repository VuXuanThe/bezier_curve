package com.org.ktdh_vuxuanthe.ware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.org.ktdh_vuxuanthe.ware.bezier_algorithm.BezierActivity;
import com.org.ktdh_vuxuanthe.ware.databinding.ActivitySplashBinding;
import com.org.ktdh_vuxuanthe.ware.draw_bezier.BezierTouchActivity;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initListener();
    }

    private void initListener(){
        binding.btnApply.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        binding.btnDrawBezier.setOnClickListener(view -> {
            //startActivity(new Intent(this, BezierTouchActivity.class));
        });

        binding.btnAlgorithmBezier.setOnClickListener(view -> {
            startActivity(new Intent(this, BezierActivity.class));
        });
    }
}