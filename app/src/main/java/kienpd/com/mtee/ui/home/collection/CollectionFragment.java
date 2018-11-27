package kienpd.com.mtee.ui.home.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.DetailCollectionAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.home.detail.DetailFragment;

import static android.widget.GridLayout.VERTICAL;

public class CollectionFragment extends BaseDialog implements CollectionMvpView, DetailCollectionAdapter.DetailCollectionAdapterCallback {


    private CollectionMvpPresenter<CollectionMvpView> mPresenter;
    public static final String TAG = "COLLECTION_FRAGMENT";
    public static final String EXTRAS_COLLECTION_ID = "extras_collection_id";
    public static final String EXTRAS_CATEGORY_ID = "extras_category_id";


    @BindView(R.id.recycler_detail_collection)
    RecyclerView mRecyclerDetailCollection;

    private DetailCollectionAdapter mAdapter;

    public static CollectionFragment newInstance(int collectionId, int categoryId) {
        CollectionFragment f = new CollectionFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRAS_COLLECTION_ID, collectionId);
        args.putInt(EXTRAS_CATEGORY_ID, categoryId);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new CollectionPresenter<>();
        mPresenter.onAttach(this);

        return view;
    }


    @Override
    protected void setUp(View view) {
        DividerItemDecoration itemDecor = new DividerItemDecoration(getBaseActivity(), VERTICAL);
        itemDecor.setDrawable(getBaseActivity().getResources().getDrawable(R.drawable.divider));

        mAdapter = new DetailCollectionAdapter(getBaseActivity(), new ArrayList<Voucher>(), this);
        LinearLayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerDetailCollection.setLayoutManager(layoutManager);
        if (mRecyclerDetailCollection.getItemDecorationCount() == 0) {
            mRecyclerDetailCollection.addItemDecoration(itemDecor);
        }
        mRecyclerDetailCollection.setAdapter(mAdapter);

        //Load Data
        int mCollectionId = getArguments().getInt(EXTRAS_COLLECTION_ID);
        int mCategoryId = getArguments().getInt(EXTRAS_CATEGORY_ID);
        mPresenter.loadDataByCollectionId(mCollectionId, mCategoryId, true);
    }


    @Override
    public void onClickDetailCollectionListener(int id) {
        DetailFragment fragment = DetailFragment.newInstance(id);
        fragment.show(getFragmentManager(), DetailFragment.TAG);
    }

    @Override
    public void updateRepoDetailCollection(List<Voucher> voucherList, Boolean isClearData) {
        mAdapter.addItemCollection(voucherList, isClearData);
    }
}
