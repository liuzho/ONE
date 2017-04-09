package com.liuzh.one.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.liuzh.one.application.App;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 刘晓彬 on 2017/4/9.
 */

public class FileUtil {

    public static void savePic(Context context, String url) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
                return;
            }
        }
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String fileName = System.currentTimeMillis() + ".png";
                File dir = new File(getOnePath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(getOnePath() + "/" + fileName);
                FileOutputStream os;
                try {
                    file.createNewFile();
                    os = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                App.showToast("图片保存路径：\nSD卡/ONE/" + fileName);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(context)
                .load(url)
                .into(target);
    }

    public static String getOnePath() {
        return Environment.getExternalStorageDirectory() + "/ONE";
    }

}
