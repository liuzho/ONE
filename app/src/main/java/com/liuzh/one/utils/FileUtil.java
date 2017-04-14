package com.liuzh.one.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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
    private static final String TAG = "FileUtil";
    public static void savePic(final Context context, String url) {
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
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.i(TAG, "onBitmapLoaded: " + Thread.currentThread());
                //事实证明这里是在主线程执行的，改吧，改到子线程去做
                //暂时为了方便，不用框架和麻烦的写法，直接new一个线程吧
                //也不行，这样没办法弹出Toast
                //那就new AsyncTask吧
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
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
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        App.showToast("图片保存路径：SD卡/ONE/");
                    }
                }.execute();

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
