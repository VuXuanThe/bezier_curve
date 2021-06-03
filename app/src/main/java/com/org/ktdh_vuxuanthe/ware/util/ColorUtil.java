package com.org.ktdh_vuxuanthe.ware.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;

public class ColorUtil {

    public static float[] toOpenGlColor(@ColorInt int color) {
        return new float[] {
                Color.red(color) / 255f,
                Color.green(color) / 255f,
                Color.blue(color) / 255f,
                Color.alpha(color) / 255f
        };
    }
}