package com.example.fileexplore.CustomView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.fileexplore.R;
import com.example.fileexplore.Utils.Tools.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 铖哥 on 2017/5/8.
 */

public class MotionLock extends View implements Runnable {

    private int width = 0;
    private int height = 0;
    private int large_radius = 0;
    private int small_radius = 0;
    private Paint mPaint = new Paint();
    private TypedArray typedArray;
    private int small_circle_color;
    private int large_circle_color;
    private int line_color;
    private int white_radius = 0;
    private int[][][] realPos = new int[3][3][2];
    private List<Map<String, Integer>> virtualPos = new ArrayList<>();
    private List<Map<String, Integer>> virtualPassword = new ArrayList<>();
    private float eventX;
    private float eventY;
    private boolean isScaleLarge = false;


    private OnPasswordListener mOnPasswordListener;
    private OnResultListener mOnResultListener;


    public void setOnResultListener(OnResultListener listener) {
        mOnResultListener = listener;
    }

    public void setOnPasswordListener(OnPasswordListener listener) {
        mOnPasswordListener = listener;
    }

    public interface OnResultListener {
        void onResult(boolean result);
    }

    public interface OnPasswordListener {
        void onResult(List<Map<String, Integer>> password);
    }


    public MotionLock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MotionLock);
        small_circle_color = typedArray.getColor(R.styleable.MotionLock_small_circle_color, Color.WHITE);
        large_circle_color = typedArray.getColor(R.styleable.MotionLock_large_circle_color, Color.BLACK);
        line_color = typedArray.getColor(R.styleable.MotionLock_line_color, Color.GRAY);

        mPaint.setColor(small_circle_color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);

        mPaint.setShadowLayer(3, 4, 3, Color.BLACK);
        setLayerType( LAYER_TYPE_SOFTWARE , mPaint);


    }

    public void setPassword(List<Map<String, Integer>> password) {
        virtualPassword.addAll(password);
    }

    public List<Map<String, Integer>> getPassword() {
        return virtualPassword;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();


        if (large_radius == 0 && small_radius == 0) {
            white_radius = large_radius = height > width ? width / 8 : height / 8;
            small_radius = height > width ? width / 32 : height / 32;

            //获取各个点的实际坐标
            for (int i = 1; i <= 3; i++)
                for (int j = 1; j <= 3; j++) {

                    realPos[i - 1][j - 1][0] = large_radius * (3 * j - 2);
                    realPos[i - 1][j - 1][1] = large_radius * (3 * i - 2);

                }

        }


        //画出所有的圆
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {

                if (virtualPos.size() != 0) {
                    for (Map<String, Integer> e : virtualPos) {
                        if (e.get("X") == i && e.get("Y") == j) {
                            mPaint.setColor(large_circle_color);
                            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                            break;
                        }
                    }
                }

                if (virtualPos.size() != 0 &&
                        virtualPos.get(virtualPos.size() - 1).get("X") == i &&
                        virtualPos.get(virtualPos.size() - 1).get("Y") == j) {
                    mPaint.setColor(small_circle_color);
                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    canvas.drawCircle(realPos[i - 1][j - 1][0], realPos[i - 1][j - 1][1], large_radius -8, mPaint);

                    if (isScaleLarge) {
                        mPaint.setColor(large_circle_color);
                    } else {
                        mPaint.setColor(Color.WHITE);
                    }

                    canvas.drawCircle(realPos[i - 1][j - 1][0], realPos[i - 1][j - 1][1], white_radius -8, mPaint);
                    mPaint.setStyle(Paint.Style.STROKE);
                }

                canvas.drawCircle(realPos[i - 1][j - 1][0], realPos[i - 1][j - 1][1], large_radius -8, mPaint);
                canvas.drawCircle(realPos[i - 1][j - 1][0], realPos[i - 1][j - 1][1], small_radius -8, mPaint);
                mPaint.setColor(small_circle_color);
                mPaint.setStyle(Paint.Style.STROKE);


            }
        }

        if (virtualPos.size() != 0) {
            mPaint.setColor(line_color);

            for (int i = 0; i < virtualPos.size() - 1; i++) {
                canvas.drawLine(
                        getRealX(i),
                        getRealY(i),
                        getRealX(i + 1),
                        getRealY(i + 1),
                        mPaint
                );

            }

            canvas.drawLine(
                    getRealX(virtualPos.size() - 1),
                    getRealY(virtualPos.size() - 1),
                    eventX, eventY, mPaint
            );
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Map<String, Integer> tMap;
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    postDelayed(this, 5);
                    if ((tMap = posInCircle(event.getX(), event.getY(), large_radius)) != null) {
                        if (!compareAll(virtualPos, tMap))
                            virtualPos.add(tMap);
                        isScaleLarge = false;
                        invalidate();
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    eventX = event.getX();
                    eventY = event.getY();
                    if ((tMap = posInCircle(event.getX(), event.getY(), large_radius)) != null) {

                        if (!compareAll(virtualPos, tMap))
                            virtualPos.add(tMap);
                        isScaleLarge = false;
                        invalidate();
                    }
                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:

                    if (mOnPasswordListener != null)
                        mOnPasswordListener.onResult(virtualPos);

                    if (mOnResultListener != null)
                        if (virtualPassword.equals(virtualPos)) {
                            mOnResultListener.onResult(true);
                        } else {
                            mOnResultListener.onResult(false);
                        }

                    virtualPos.clear();
                    mPaint.setStyle(Paint.Style.STROKE);
                    removeCallbacks(this);

                    invalidate();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public void run() {

        Log.e(isScaleLarge);
        int i = 16;

        if (white_radius >= 0 && !isScaleLarge) {
            white_radius = white_radius - i;
            i += 16;
        } else {
            isScaleLarge = true;
        }

        if (white_radius <= large_radius - 4 && isScaleLarge) {
            white_radius = white_radius + i;
            i -= 16;
        }

        invalidate();
        postDelayed(this, 5);


    }

    private boolean compareAll(List<Map<String, Integer>> list, Map<String, Integer> map) {

        for (Map<String, Integer> e : list) {
            if (e.equals(map)) {
                return true;
            }
        }

        return false;
    }

    private Map<String, Integer> posInCircle(float X, float Y, float radius) {

        Map<String, Integer> map = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if ((Math.pow(realPos[i - 1][j - 1][0] - X, 2) + Math.pow(realPos[i - 1][j - 1][1] - Y, 2)) < Math.pow(radius, 2)) {
                    map.put("X", i);
                    map.put("Y", j);
                    return map;
                }
            }
        }
        return null;
    }

    private int getRealX(int i) {
        return realPos[virtualPos.get(i).get("X") - 1][virtualPos.get(i).get("Y") - 1][0];
    }

    private int getRealY(int i) {
        return realPos[virtualPos.get(i).get("X") - 1][virtualPos.get(i).get("Y") - 1][1];
    }

}
