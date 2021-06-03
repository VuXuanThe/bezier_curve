package com.org.ktdh_vuxuanthe.ware.draw_bezier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.org.ktdh_vuxuanthe.ware.R;
import com.org.ktdh_vuxuanthe.ware.databinding.ActivityBezierTouchBinding;

public class BezierTouchActivity extends AppCompatActivity {
    private ActivityBezierTouchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bezier_touch);
        binding.button2.setOnClickListener(view -> {
            binding.bezierDraw2.render();
        });
    }
}