package vn.com.viettel.vtcc.browser.dragflowlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * the alert window helper
 * Created by heaven7 on 2016/8/4.
 */
public class AlertWindowHelper {

    private final WindowManager mWm;
    private final WindowManager.LayoutParams mParams;
    private final float mTouchSlop;
    private final int mStateBarHeight;
    private final boolean mFullScreen;

    private View mView;
    private ICallback mCallback;
    private int mInitLeft;
    private int mInitTop;
    private int mTouchDownX;
    private int mTouchDownY;

    /**
     * the drag event callback
     */
    public interface ICallback {

        void onCancel(View v, MotionEvent event);

        boolean onMove(View view, MotionEvent event);
    }

    public AlertWindowHelper(Context context) {
        this.mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mParams = createWindowParams();
        this.mStateBarHeight = ViewUtils.getStatusHeight(context);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mFullScreen = context instanceof Activity && (((Activity) context).getWindow()
                .getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN ) != 0 ;
    }

    public View getView() {
        return mView;
    }

    /***
     *  set the touched down x and y coordinate.
     * @param x the x
     * @param y the y
     */
    public void setTouchDownPosition(int x, int y) {
        this.mTouchDownX = x;
        this.mTouchDownY = y ;
    }

    private WindowManager.LayoutParams createWindowParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        return params;
    }

    /**
     * show the view by target position.
     *
     * @param view     the view in window
     * @param initLeft the init eft
     * @param initTop  the init top
     */
    public void showView(View view, int initLeft, int initTop) {
        showView(view, initLeft, initTop, false, null);
    }

    /**
     * show the view by target position.
     *
     * @param view            the view in window
     * @param initLeft        the init eft
     * @param initTop         the init top
     * @param useInternalDrag if use drag in internal. if true it will set the internal onTouch listener.
     * @param callback        callback ,can be null
     */
    public void showView(View view, int initLeft, int initTop, boolean useInternalDrag, ICallback callback) {
        releaseView();
        this.mInitLeft = initLeft;
        this.mInitTop = initTop;
        this.mView = view;
        if (callback != null) {
            mCallback = callback;
        }
        if (useInternalDrag) {
            view.setOnTouchListener(new DragTouchListener());
        }
        DragFlowLayout.sDebugger.d("showView", "initLeft = " + initLeft + " ," +
                "initTop = " + initTop);
        mParams.x = initLeft;
        mParams.y = adjustY(initTop);
        mWm.addView(view, mParams);
    }

    /**
     * update view layout by delta x and y.
     *
     * @param dx the delta x
     * @param dy the delta y
     */
    public void updateViewLayout2(int dx, int dy) {
        if (mView == null) {
            throw new IllegalStateException("must call #showView first");
        }
        DragFlowLayout.sDebugger.d("updateViewLayout2", "dx = " + dx +" ,dy = " + dy);
        mParams.x = mInitLeft + dx;
        mParams.y = adjustY(mInitTop + dy);
        mWm.updateViewLayout(mView, mParams);
    }

    /**
     * update the view
     *
     * @param left the left
     * @param top  the top
     */
    public void updateViewLayout(int left, int top) {
        if (mView == null) {
            throw new IllegalStateException("must call #showView first");
        }
        mParams.x = left;
        mParams.y = adjustY(top);
        mWm.updateViewLayout(mView, mParams);
    }

    public void releaseView() {
        if (mView != null) {
            mView.setOnTouchListener(null);
            mWm.removeView(mView);
            mView = null;
        }
    }

    /**
     * adjust the y position
     * @param top the raw top/y position
     * @return the really y position
     */
    private int adjustY(int top) {
        return mFullScreen ? top : top - mStateBarHeight;
    }

    private boolean checkTouchSlop(float dx, float dy) {
        return Math.abs(dx) > mTouchSlop || Math.abs(dy) > mTouchSlop;
    }

    private class DragTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final ICallback mCallback = AlertWindowHelper.this.mCallback;
            if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                if (mCallback != null) {
                    mCallback.onCancel(v, event);
                }
                return false;
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int dx = (int) event.getRawX() - mTouchDownX;
                int dy = (int) event.getRawY() - mTouchDownY;
                if(!checkTouchSlop(dx , dy)) {
                    return false;
                }
                updateViewLayout2(dx, dy);
                return mCallback != null && mCallback.onMove(v, event);
            }
            return false;
        }
    }

}
