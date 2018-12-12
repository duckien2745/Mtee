package kienpd.com.mtee.ui.adapter.holder;

import android.view.View;

import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public abstract class CommentViewHolder extends BaseViewHolder {

    private int mCurrentPosition;

    public CommentViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind() {

    }

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public void onBind(RatingResponse ratingResponse) {
        clear();
    }


    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
