package com.liuzh.one.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
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


    private boolean mInTop = true;
    private int mPos = 0;//当前能获取到的最顶部的view的position
    private float mPosTop;//当前可获取view的top坐标
    private OnTopListener mOnTopListener;
    private OnLeaveTopListener mOnLeaveTopListener;
    private boolean mPerformedLeaveListener = false;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LayoutManager layoutManager = getLayoutManager();
        //找到能找到的最顶部的非空的view
        View v = layoutManager.findViewByPosition(mPos);
        //v==null，表示这个position找不到view了，将mPos++并结束方法，继续找最顶部的view
        if (v == null) {
            mPos++;
            return;
        }
        //更新当前可获取view的top坐标
        mPosTop = v.getTop();
        //如果当前view的top大于等于0（就是完全露出来了），并且当前view的position不是0，即不是第一个
        //就需要将mPos回减，因为此时表示RecyclerView的内容在向下滚动
        if (mPosTop >= 0 && mPos != 0) {
            mPos--;
        }

        //如果当前view的position是0，并且当前view的top也是0，表示滑动到了顶部
        mInTop = mPos == 0 && mPosTop == 0;
        if (mInTop) {
            //滑动到顶部，执行顶部监听
            if (mOnTopListener != null) {
                mOnTopListener.onTopListener();
            }
            //设置离开顶部的监听为未执行状态
            mPerformedLeaveListener = false;
        } else if (!mPerformedLeaveListener) {
            //如果不在顶部，并且没有执行过离开顶部监听
            //执行离开顶部监听
            if (mOnLeaveTopListener != null) {
                mOnLeaveTopListener.onLeaveTopListener();
            }
            //设置离开顶部监听为已执行状态
            mPerformedLeaveListener = true;
        }
    }

    public interface OnTopListener {
        void onTopListener();
    }

    public void setOnTopListener(OnTopListener listener) {
        mOnTopListener = listener;
    }

    public interface OnLeaveTopListener {
        void onLeaveTopListener();
    }

    public void setOnLeaveTopListener(OnLeaveTopListener listener) {
        mOnLeaveTopListener = listener;
    }

    public boolean isInTop() {
        return mInTop;
    }
}
