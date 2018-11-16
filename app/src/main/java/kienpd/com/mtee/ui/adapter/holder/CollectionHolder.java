package kienpd.com.mtee.ui.adapter.holder;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.ui.adapter.CollectionAdapter;

import static android.widget.GridLayout.HORIZONTAL;

public class CollectionHolder extends HomeViewHolder implements CollectionAdapter.CollectionAdapterCallback {

    @BindView(R.id.recycler_collection)
    RecyclerView mRecyclerCollection;

    private Context mContext;
    private CollectionHolderCallback mCallback;

    public CollectionHolder(Context context, View itemView, CollectionHolderCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(List<Collection> arrayList) {
        DividerItemDecoration itemDecor = new DividerItemDecoration(mContext, HORIZONTAL);
        itemDecor.setDrawable(mContext.getResources().getDrawable(R.drawable.divider));

        CollectionAdapter adapter = new CollectionAdapter(mContext, arrayList, this);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerCollection.setLayoutManager(layoutManager);
        if (mRecyclerCollection.getItemDecorationCount() == 0) {
            mRecyclerCollection.addItemDecoration(itemDecor);
        }
        mRecyclerCollection.setAdapter(adapter);
    }

    @Override
    public void onClickCollectionListener(int position) {
        mCallback.onClickCollectionHolderListener(position);
    }

    public interface CollectionHolderCallback {
        void onClickCollectionHolderListener(int position);
    }

}
