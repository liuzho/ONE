package com.liuzh.one.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * bitmap工具类
 * Created by LiuZh on 2017/3/15.
 */

public class BitmapUtil {

    /**
     * 生成一个圆形的图片
     *
     * @param src 原图片
     * @return 圆形图片
     */
    public static Bitmap createCircleBitmap(Bitmap src) {
        int size = Math.min(src.getWidth(), src.getHeight());

        int x = (src.getWidth() - size) / 2;
        int y = (src.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(src, x, y, size, size);
        if (squaredBitmap != src) {
            src.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, src.getConfig());

        if (bitmap == null) {
            return src;
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }
}
