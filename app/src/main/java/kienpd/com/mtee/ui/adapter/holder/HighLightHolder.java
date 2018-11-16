package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.HighLightAdapter;

import static android.widget.GridLayout.HORIZONTAL;

public class HighLightHolder extends HomeViewHolder implements HighLightAdapter.HighLightAdapterCallback {

    @BindView(R.id.recycler_highlight)
    RecyclerView mRecyclerHighLight;

    private Context mContext;
    private HighLightHolderCallback mCallback;

    public HighLightHolder(Context context, View itemView, HighLightHolderCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(ArrayList<Voucher> arrayList) {
        DividerItemDecoration itemDecor = new DividerItemDecoration(mContext, HORIZONTAL);
        itemDecor.setDrawable(mContext.getResources().getDrawable(R.drawable.divider));

        HighLightAdapter adapter = new HighLightAdapter(mContext, arrayList, this);
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false);
        mRecyclerHighLight.setLayoutManager(layoutManager);
        if (mRecyclerHighLight.getItemDecorationCount() == 0) {
            mRecyclerHighLight.addItemDecoration(itemDecor);
        }
        mRecyclerHighLight.setAdapter(adapter);
    }

    @Override
    public void onClickHighLightListener(int position) {
        mCallback.onClickHighLightHolderListener(position);
    }

    public interface HighLightHolderCallback {
        void onClickHighLightHolderListener(int position);
    }
}
