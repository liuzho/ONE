package com.liuzh.one.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * application
 * Created by 刘晓彬 on 2017/3/16.
 */

public class App extends Application {

    private static Context mContext;
    private static Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
