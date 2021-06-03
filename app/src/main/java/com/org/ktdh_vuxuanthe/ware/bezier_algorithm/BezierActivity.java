package com.org.ktdh_vuxuanthe.ware.bezier_algorithm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.org.ktdh_vuxuanthe.ware.R;
import com.org.ktdh_vuxuanthe.ware.databinding.ActivityBezierBinding;

public class BezierActivity extends AppCompatActivity {
    private ActivityBezierBinding binding;
    private BezierAlgorithmRender mBezierRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bezier);
        mBezierRenderer = new BezierAlgorithmRender(this);
        binding.glsurfaceview.setEGLContextClientVersion(2);
        binding.glsurfaceview.setRenderer(mBezierRenderer);
        binding.glsurfaceview.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}