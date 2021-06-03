package com.org.ktdh_vuxuanthe.ware.draw_bezier;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BezierDrawView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private BezierTouchCurve mBezierTouchCurve;
    private float currentX;
    private float currentY;
    private List<TimedPoint> mPointsCache = new ArrayList<>();
    private ControlTimedPoints mControlTimedPointsCached = new ControlTimedPoints();
    private List<TimedPoint> mPoints;
//    private Bezier mBezierCached = new Bezier();

    private float num = 0f;
    private int delta = 200;

    public BezierDrawView(Context context) {
        super(context);
        init();
    }

    public BezierDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mPoints = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        currentX = event.getX();
        currentY = event.getY();

        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPoints.clear();
                addPoint(getNewPoint(eventX, eventY));
                break;
            case MotionEvent.ACTION_MOVE:
                addPoint(getNewPoint(eventX, eventY));
                break;
            case MotionEvent.ACTION_UP:
                mBezierTouchCurve.touchUp();
                break;
        }

        return true;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 1.0f);
        mBezierTouchCurve = new BezierTouchCurve(getContext(),this);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mBezierTouchCurve.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        num++;
        mBezierTouchCurve.drawTextureAndScreen();
    }


    private void recyclePoint(TimedPoint point) {
        mPointsCache.add(point);
    }


    private void addPoint(TimedPoint newPoint) {
        mPoints.add(newPoint);

        int pointsCount = mPoints.size();
        if (pointsCount > 3) {

            ControlTimedPoints tmp = calculateCurveControlPoints(mPoints.get(0), mPoints.get(1), mPoints.get(2));
            TimedPoint c2 = tmp.c2;
            recyclePoint(tmp.c1);

            tmp = calculateCurveControlPoints(mPoints.get(1), mPoints.get(2), mPoints.get(3));
            TimedPoint c3 = tmp.c1;
            recyclePoint(tmp.c2);


            list.add(new Bezier(mPoints.get(1), c2, c3, mPoints.get(2)));

            recyclePoint(mPoints.remove(0));

            recyclePoint(c2);
            recyclePoint(c3);

        } else if (pointsCount == 1) {
            TimedPoint firstPoint = mPoints.get(0);
            mPoints.add(getNewPoint(firstPoint.x, firstPoint.y));
        }
    }

    private ArrayList<Bezier> list = new ArrayList<>();

    public void render(){
        mBezierTouchCurve.setBezier(list);
    }



    /**
     * 计算控制点
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    private ControlTimedPoints calculateCurveControlPoints(TimedPoint s1, TimedPoint s2, TimedPoint s3) {
        float dx1 = s1.x - s2.x;
        float dy1 = s1.y - s2.y;
        float dx2 = s2.x - s3.x;
        float dy2 = s2.y - s3.y;

        float m1X = (s1.x + s2.x) / 2.0f;
        float m1Y = (s1.y + s2.y) / 2.0f;
        float m2X = (s2.x + s3.x) / 2.0f;
        float m2Y = (s2.y + s3.y) / 2.0f;

        float l1 = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1);
        float l2 = (float) Math.sqrt(dx2 * dx2 + dy2 * dy2);

        float dxm = (m1X - m2X);
        float dym = (m1Y - m2Y);
        float k = l2 / (l1 + l2);
        if (Float.isNaN(k)) k = 0.0f;
        float cmX = m2X + dxm * k;
        float cmY = m2Y + dym * k;

        float tx = s2.x - cmX;
        float ty = s2.y - cmY;

        return mControlTimedPointsCached.set(getNewPoint(m1X + tx, m1Y + ty), getNewPoint(m2X + tx, m2Y + ty));
    }

    private TimedPoint getNewPoint(float x, float y) {
        int mCacheSize = mPointsCache.size();
        TimedPoint timedPoint;
        if (mCacheSize == 0) {
            // Cache is empty, create a new point
            timedPoint = new TimedPoint();
        } else {
            // Get point from cache
            timedPoint = mPointsCache.remove(mCacheSize - 1);
        }

        return timedPoint.set(x, y);
    }
}

