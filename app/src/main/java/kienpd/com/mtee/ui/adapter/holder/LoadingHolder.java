package kienpd.com.mtee.ui.adapter.holder;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;

public class LoadingHolder extends HomeViewHolder {


    @BindView(R.id.text_load_more)
    TextView mTextLoadMore;

    @BindView(R.id.progress_load_more)
    ProgressBar mProgressLoadMore;

    @BindView(R.id.layout_load_more)
    LinearLayout mLayoutLoadMore;

    private Context mContext;
    private LoadingListener mCallback;
    private boolean mIsLoading;


    public LoadingHolder(Context context, View itemView, LoadingListener callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mCallback = callback;
    }


    @Override
    protected void clear() {

    }

    @Override
    public void onBind() {
        super.onBind();
        mProgressLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsLoading) {
                    mCallback.onLoadMoreClick(LoadingHolder.this);
                }
            }
        });

    }

    public interface LoadingListener {
        void onLoadMoreClick(LoadingHolder loadingViewHolder);
    }

    public void setLoading(boolean isLoading) {
        mLayoutLoadMore.setVisibility(View.VISIBLE);
        mIsLoading = isLoading;
        bindData();
    }

    public void hideLoading() {
        mLayoutLoadMore.setVisibility(View.GONE);
    }

    private void bindData() {
        if (mIsLoading) {
            mProgressLoadMore.setVisibility(View.VISIBLE);
            mTextLoadMore.setText(mContext.getResources().getString(R.string.title_loading_more));
        } else {
            mProgressLoadMore.setVisibility(View.GONE);
            mTextLoadMore.setText(mContext.getResources().getString(R.string.title_load_more));
        }
    }

    public void setEmpty() {
        mProgressLoadMore.setVisibility(View.GONE);
        mTextLoadMore.setText(mContext.getResources().getString(R.string.title_no_data));
    }


    public void setLoadingBlack() {
        mTextLoadMore.setTextColor(Color.WHITE);
    }
}
