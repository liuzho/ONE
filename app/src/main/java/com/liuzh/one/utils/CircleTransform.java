package com.liuzh.one.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
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
