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
import kienpd.com.mtee.data.model.modelTest;
import kienpd.com.mtee.ui.adapter.HighLightAdapter;
import kienpd.com.mtee.ui.base.BaseViewHolder;

import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.GridLayout.VERTICAL;

public class HighLightHolder extends BaseViewHolder {

    @BindView(R.id.recycler_highlight)
    RecyclerView mRecyclerHighLight;

    private Context mContext;

    public HighLightHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(ArrayList<modelTest> arrayList) {
        DividerItemDecoration itemDecor = new DividerItemDecoration(mContext, HORIZONTAL);
        itemDecor.setDrawable(mContext.getResources().getDrawable(R.drawable.divider));

        HighLightAdapter adapter = new HighLightAdapter(mContext, arrayList);
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false);
        mRecyclerHighLight.setLayoutManager(layoutManager);
        if (mRecyclerHighLight.getItemDecorationCount() == 0) {
            mRecyclerHighLight.addItemDecoration(itemDecor);
        }
        mRecyclerHighLight.setAdapter(adapter);
    }
}
