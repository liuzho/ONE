package com.liuzh.one.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Picasso裁切圆形图
 * Created by 刘晓彬 on 2017/3/21.
 */

public class CircleTransform implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        return BitmapUtil.createCircleBitmap(source);
    }

    @Override
    public String key() {
        return "circle";
    }
}
