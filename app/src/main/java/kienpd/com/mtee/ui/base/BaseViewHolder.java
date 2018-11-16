package kienpd.com.mtee.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int mCurrentPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }


    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}

