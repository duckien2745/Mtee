package kienpd.com.mtee.ui.adapter.holder;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public abstract class HomeViewHolder extends BaseViewHolder {

    private int mCurrentPosition;

    public HomeViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind() {

    }

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public void onBind(List<Collection> arrayList) {
        clear();
    }

    public void onBind(ArrayList<Voucher> arrayList) {
        clear();
    }


    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}

