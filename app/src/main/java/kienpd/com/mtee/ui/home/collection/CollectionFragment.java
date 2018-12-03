package kienpd.com.mtee.ui.home.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.DetailCollectionAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.CommonUtils;

public class CollectionFragment extends BaseDialog implements CollectionMvpView, DetailCollectionAdapter.DetailCollectionAdapterCallback {


    private CollectionMvpPresenter<CollectionMvpView> mPresenter;
    public static final String TAG = "COLLECTION_FRAGMENT";
    public static final String EXTRAS_COLLECTION_ID = "extras_collection_id";
    public static final String EXTRAS_CATEGORY_ID = "extras_category_id";

    @BindView(R.id.recycler_detail_collection)
    RecyclerView mRecyclerDetailCollection;

    @BindView(R.id.process_loading)
    ProgressBar mProgressBar;

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
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerDetailCollection.setVisibility(View.GONE);

        int px = CommonUtils.dpToPx(8);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 2);
        LinearLayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 2, GridLayoutManager.VERTICAL, false);

        mAdapter = new DetailCollectionAdapter(getBaseActivity(), new ArrayList<Voucher>(), this);
        mRecyclerDetailCollection.setLayoutManager(layoutManager);
        mRecyclerDetailCollection.addItemDecoration(itemDecoration);
        mRecyclerDetailCollection.setAdapter(mAdapter);

        //Load Data
        int mCollectionId = getArguments().getInt(EXTRAS_COLLECTION_ID);
        int mCategoryId = getArguments().getInt(EXTRAS_CATEGORY_ID);
        mPresenter.loadDataByCollectionId(mCollectionId, mCategoryId, true);
    }


    @Override
    public void onClickDetailCollectionListener(int id) {
        VoucherFragment fragment = VoucherFragment.newInstance(id);
        fragment.show(getFragmentManager(), VoucherFragment.TAG);
    }

    @Override
    public void updateRepoDetailCollection(List<Voucher> voucherList, Boolean isClearData) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerDetailCollection.setVisibility(View.VISIBLE);

        mAdapter.addItemCollection(voucherList, isClearData);
    }
}
