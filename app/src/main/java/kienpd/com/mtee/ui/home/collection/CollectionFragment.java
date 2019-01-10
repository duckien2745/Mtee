package kienpd.com.mtee.ui.home.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    public static final String EXTRAS_TITLE = "extras_title";

    @BindView(R.id.recycler_detail_collection)
    RecyclerView mRecyclerDetailCollection;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    @BindView(R.id.layout_refresh)
    LinearLayout mLayoutRefresh;

    @BindView(R.id.text_tab_to_refresh)
    TextView mTextRefresh;

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.text_title_center)
    TextView mTextTitleCenter;

    private DetailCollectionAdapter mAdapter;
    private int mCollectionId;
    private int mCategoryId;

    public static CollectionFragment newInstance( int collectionId, int categoryId) {
        CollectionFragment f = new CollectionFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRAS_COLLECTION_ID, collectionId);
        args.putInt(EXTRAS_CATEGORY_ID, categoryId);
//        args.putString(EXTRAS_TITLE, title);
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
        int px = CommonUtils.dpToPx(8);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 2);
        LinearLayoutManager layoutManager = new GridLayoutManager(getBaseActivity(), 2, GridLayoutManager.VERTICAL, false);

        mSwipeToRefresh.setColorSchemeResources(R.color.color_item_select);
        mAdapter = new DetailCollectionAdapter(getBaseActivity(), new ArrayList<Voucher>(), this);
        mRecyclerDetailCollection.setLayoutManager(layoutManager);
        mRecyclerDetailCollection.addItemDecoration(itemDecoration);
        mRecyclerDetailCollection.setAdapter(mAdapter);


        //Load Data
        mCollectionId = getArguments().getInt(EXTRAS_COLLECTION_ID);
        mCategoryId = getArguments().getInt(EXTRAS_CATEGORY_ID);
        mTextTitleCenter.setText("Bộ sưu tập");


        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadDataByCollectionId(mCollectionId, mCategoryId, true);
            }
        });

        mTextRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeToRefresh.setRefreshing(true);
                mPresenter.loadDataByCollectionId(mCollectionId, mCategoryId, true);
            }
        });

        mSwipeToRefresh.setRefreshing(true);
        mPresenter.loadDataByCollectionId(mCollectionId, mCategoryId, true);
    }

    @Override
    public void onBackButtonPressed() {
        dismissDialog(TAG);
    }


    @Override
    public void onClickDetailCollectionListener(int id) {
        VoucherFragment fragment = VoucherFragment.newInstance(id);
        fragment.show(getFragmentManager(), VoucherFragment.TAG);
    }

    @Override
    public void updateRepoDetailCollection(List<Voucher> voucherList, Boolean isClearData) {
        mSwipeToRefresh.setRefreshing(false);

        if (voucherList.size() > 0) {
            mRecyclerDetailCollection.setVisibility(View.VISIBLE);
            mLayoutRefresh.setVisibility(View.GONE);
            mAdapter.addItemCollection(voucherList, isClearData);
        } else {
            mRecyclerDetailCollection.setVisibility(View.GONE);
            mLayoutRefresh.setVisibility(View.VISIBLE);
        }
    }
}
