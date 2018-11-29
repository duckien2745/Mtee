package kienpd.com.mtee.ui.adapter.holder;

import android.view.View;

import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public abstract class FollowViewHolder extends BaseViewHolder {

    private int mCurrentPosition;

    public FollowViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind() {

    }

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public void onBind(Store store) {
        clear();
    }


    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
