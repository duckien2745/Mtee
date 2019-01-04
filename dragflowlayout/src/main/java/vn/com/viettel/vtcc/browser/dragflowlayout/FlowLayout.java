package vn.com.viettel.vtcc.browser.dragflowlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Deprecated
public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";
    private static final boolean sDebug = false;
    private int mMaxLine = Integer.MAX_VALUE;
    private boolean mHasMoreByMaxLine;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlowLayout(Context context) {
        super(context);
        init(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
            this.mMaxLine = a.getInt(a.getIndex(R.styleable.FlowLayout_flowLayout_maxLine), Integer.MAX_VALUE);
            a.recycle();
        }
    }

    public void setMaxLine(int maxLine) {
        if (maxLine > 0 && maxLine != mMaxLine) {
            this.mMaxLine = maxLine;
            requestLayout();
            invalidate();
        }
    }

    public int getMaxLine(){
        return mMaxLine;
    }

    /**
     * has more line by the max line.
     * @return  true if has more line
     */
    public boolean hasMoreLine(){
        return mHasMoreByMaxLine;
    }

    public int getRealLineCount(){
        return mLineHeights.size();
    }

    @Override
    protected LayoutParams generateLayoutParams(
            LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        //must wrap content
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        mHasMoreByMaxLine = false;

        if (sDebug)
            Log.e(TAG, sizeWidth + "," + sizeHeight);

        int width = 0;
        int height = 0;

        int lineWidth = 0;

        int lineHeight = 0;

        int cCount = getChildCount();

        int rows = 1;
        boolean limited = false;

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth) {
                if(!limited) {
                    width = Math.max(lineWidth, childWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;

                    rows ++;
                    if (rows == mMaxLine) {
                        limited = true;
                    }
                    mHasMoreByMaxLine = false;
                }else{
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                    mHasMoreByMaxLine = true;
                    break;
                }
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                (modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);

    }


    private List<List<View>> mAllViews = new ArrayList<List<View>>();

    private List<Integer> mLineHeights = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeights.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                mLineHeights.add(lineHeight);
                mAllViews.add(lineViews);
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        mLineHeights.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeights.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                if (sDebug)
                    Log.e(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
                            + rc + " , b = " + bc);

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }

    }
}
