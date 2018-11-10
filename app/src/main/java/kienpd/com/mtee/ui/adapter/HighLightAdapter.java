package kienpd.com.mtee.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.modelTest;
import kienpd.com.mtee.ui.adapter.holder.OrderHolder;
import kienpd.com.mtee.ui.base.BaseViewHolder;

public class HighLightAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<modelTest> mList;
    private Context mContext;

    public HighLightAdapter(Context context, ArrayList<modelTest> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderHolder holder = new OrderHolder(mContext,
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));

        int pxPadding = mContext.getResources().getDimensionPixelSize(R.dimen.padding_item_home);
        int w = (Resources.getSystem().getDisplayMetrics().widthPixels - pxPadding * 4) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.mLinearLayout.setLayoutParams(layoutParams);

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return 18;
    }
}
