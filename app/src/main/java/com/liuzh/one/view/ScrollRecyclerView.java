package com.liuzh.one.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * Created by 刘晓彬 on 2017/4/6.
 */

public class ScrollRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private boolean mIsInTop = true;
    private int mPos = 0;//当前能获取到的最顶部的view的position
    private float mPosTop;//当前可获取view的top坐标
    private float mTop;//RecyclerView最顶部的Y坐标

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LayoutManager layoutManager = getLayoutManager();
        //找到能找到的最顶部的非空的view
        View v = layoutManager.findViewByPosition(mPos);
        //v==null，表示这个position找不到view了，将mPos++结束方法，继续找最顶部的view
        if (v == null) {
            mPos++;
            return;
        }
        //更新当前可获取view的top坐标
        mPosTop = v.getTop();
        //如果当前view的top大于等于0（就是完全露出来了），并且当前view的position不是0，即不是第一个
        //就需要将mPos回减，因为此时表示RecyclerView在向下滚动
        if (mPosTop >= 0 && mPos != 0) {
            mPos--;
        }
        //如果当前view的position是0，并且当前view的top也是0，表示滑动到了顶部
        mIsInTop = mPos == 0 && mPosTop == 0;
    }

    public boolean isInTop() {
        return mIsInTop;
    }
}
