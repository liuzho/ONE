package com.liuzh.one.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 只能是正方形
 * Created by 刘晓彬 on 2017/3/15.
 */

public class CDView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "CDView";
    private Paint mPaint;//画笔
    private int mCDRadius;//CD宽度
    private int mViewWidth;//view宽度
    private int mCenterXY;//中心点的xy坐标
    private int mBtnCRadius;//中心可点击的圆的半径
    private float mPosRotate;//当前CD的旋转角度
    private boolean mIsPlaying;//是否处于播放状态
    private Timer mTimer;//定时器
    private float mDownX;//按下时的X坐标
    private float mDownY;//按下时的Y坐标
    private OnPlayListener mPlayListener;//开始播放的监听
    private OnStopListener mStopListener;//停止播放的监听

    public CDView(Context context) {
        this(context, null);
    }

    public CDView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CDView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化工作
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mIsPlaying = false;
        mPosRotate = 0;
        mTimer = new Timer();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //通过宽高设置view的宽度，取最小值，设置view为正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = Math.min(width, height);
        setMeasuredDimension(mViewWidth, mViewWidth);
        //通过padding设置CD的宽度
        float VPadding = getPaddingTop() + getPaddingBottom();
        float HPadding = getPaddingLeft() + getPaddingRight();
        mCDRadius = (int) ((mViewWidth - Math.max(VPadding, HPadding)) / 2);
        mCenterXY = mViewWidth / 2;
        mBtnCRadius = mCDRadius / 6;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(-mPosRotate, mViewWidth / 2, mViewWidth / 2);
        drawCD(canvas);
        drawCenter(canvas);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(0x88eeeeee);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCenterXY, mCenterXY, mCDRadius - 5, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 绘制CD的图片
     *
     * @param canvas 画布
     */
    private void drawCD(Canvas canvas) {
        if (getDrawable() == null) {
            mPaint.setColor(0XFFEEEEEE);
            canvas.drawCircle(mCenterXY, mCenterXY, mCDRadius, mPaint);
        }
    }


    /**
     * 绘制中心的点击播放区域，小圆圈和三角形
     *
     * @param canvas 画布
     */
    private void drawCenter(Canvas canvas) {
        //绘制圆
        mPaint.setColor(Color.argb(120, 220, 220, 220));
        canvas.drawCircle(mCenterXY, mCenterXY, mBtnCRadius, mPaint);
        mPaint.setColor(Color.WHITE);
        if (mIsPlaying) {
            //绘制暂停按钮形态
            Rect rightRect = new Rect((int) (mCenterXY + mBtnCRadius * Math.sqrt(3) / 6f - 2f),
                    (int) (mCenterXY - mBtnCRadius / 2f),
                    (int) (mCenterXY + mBtnCRadius * Math.sqrt(3) / 6f),
                    (int) (mCenterXY + mBtnCRadius / 2f));

            Rect leftRect = new Rect((int) (mCenterXY - mBtnCRadius * Math.sqrt(3) / 6f),
                    (int) (mCenterXY - mBtnCRadius / 2f),
                    (int) (mCenterXY - mBtnCRadius * Math.sqrt(3) / 6f + 2f),
                    (int) (mCenterXY + mBtnCRadius / 2f));
            canvas.drawRect(leftRect, mPaint);
            canvas.drawRect(rightRect, mPaint);
        } else {
            //绘制三角形播放按钮形态
            float triangleSide = mCDRadius / 6f;//三角形边长
            Path path = new Path();
            path.moveTo((float) (mCenterXY - triangleSide * Math.sqrt(3) / 6f),
                    mCenterXY - triangleSide / 2f);
            path.lineTo((float) (mCenterXY + triangleSide * Math.sqrt(3) / 3f),
                    mCenterXY);
            path.lineTo((float) (mCenterXY - triangleSide * Math.sqrt(3) / 6f),
                    mCenterXY + triangleSide / 2f);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                //如果没有点在可点击的区域，则不接收事件
                if (mDownX < mCenterXY - mBtnCRadius ||
                        mDownX > mCenterXY + mBtnCRadius ||
                        mDownY < mCenterXY - mBtnCRadius ||
                        mDownY > mCenterXY + mBtnCRadius) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //如果移动过，即不是点击事件，不再接收事件
                if (event.getX() != mDownX || event.getY() != mDownY) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //经过上面的排除，此处若能接受到up事件，证明是点击事件
                if (mIsPlaying) {
                    stopMusic();
                    if (mStopListener != null) {
                        mStopListener.onStop();
                    }
                } else {
                    playMusic();
                    if (mPlayListener != null) {
                        mPlayListener.onPlay();
                    }
                }
                break;
        }

        return true;
    }

    /**
     * 开始播放音乐
     * 使用timer每0.1s叠加0.6°旋转角度
     */
    private void playMusic() {
        Log.i(TAG, "playMusic");
        mIsPlaying = true;
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mPosRotate = (mPosRotate + 0.2f) % 360f;
                post(new Runnable() {
                    @Override
                    public void run() {
                        setPivotX(mViewWidth / 2);
                        setPivotY(mViewWidth / 2);
                        setRotation(mPosRotate);
                        invalidate();
                    }
                });
            }
        }, 0, 30);
    }

    /**
     * 停止播放音乐
     */
    private void stopMusic() {
        Log.i(TAG, "stopMusic");
        mIsPlaying = false;
        mTimer.cancel();
        mTimer = null;
        invalidate();
    }


    /**
     * 开始播放的监听
     */
    public interface OnPlayListener {
        void onPlay();
    }

    /**
     * 停止播放的监听
     */
    public interface OnStopListener {
        void onStop();
    }

    /**
     * 设置开始播放监听
     *
     * @param listener 监听器
     */
    public void setOnPlayListener(OnPlayListener listener) {
        this.mPlayListener = listener;
    }

    /**
     * 设置停止播放监听
     *
     * @param listener 监听器
     */
    public void setOnStopListener(OnStopListener listener) {
        this.mStopListener = listener;
    }
}
