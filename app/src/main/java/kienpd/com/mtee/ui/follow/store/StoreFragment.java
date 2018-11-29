package kienpd.com.mtee.ui.follow.store;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.DetailStoreAdapter;
import kienpd.com.mtee.ui.adapter.HomeAdapter;
import kienpd.com.mtee.ui.adapter.holder.FollowStoreAdapter;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.home.collection.CollectionFragment;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.CommonUtils;

public class StoreFragment extends BaseDialog implements StoreMvpView, DetailStoreAdapter.DetailStoreAdapterCallBack {

    StoreMvpPresenter<StoreMvpView> mPresenter;
    public static final String EXTRAS_STORE_ID = "extras_store_id";
    public static final String TAG = "Store Fragment";


    @BindView(R.id.recycler_detail_store)
    RecyclerView mRecyclerDetailStore;

    private DetailStoreAdapter mAdapter;
    private Handler mHandler;
    private Runnable mRunnable;
    private Boolean mUpdateData;

    private int mStoreId;
    private int userId = 37281321;

    public static StoreFragment newInstance(int storeId) {
        StoreFragment f = new StoreFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRAS_STORE_ID, storeId);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_store, container, false);
        setUnBinder(ButterKnife.bind(this, view));

        mPresenter = new StorePresenter<>();
        mPresenter.onAttach(this);
        return view;
    }

    @Override
    protected void setUp(View view) {
        int px = CommonUtils.dpToPx(8);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 2);

        mAdapter = new DetailStoreAdapter(getBaseActivity(), new ArrayList<Voucher>(), null, this);
        GridLayoutManager manager = new GridLayoutManager(getBaseActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == HomeAdapter.TYPE_ITEM_NEWEST ? 1 : 2;
            }
        });

        mRecyclerDetailStore.setLayoutManager(manager);
        mRecyclerDetailStore.addItemDecoration(itemDecoration);
        mRecyclerDetailStore.setAdapter(mAdapter);

        loadData();

    }


    @Override
    public void onClickListener(int type, int id) {
        if (type == 1) {
            VoucherFragment fragment = VoucherFragment.newInstance(id);
            fragment.show(getFragmentManager(), VoucherFragment.TAG);
        }
    }

    @Override
    public void onClickLoadMore() {
        //todo
    }

    @Override
    public void onClickFollow(int storeId) {
        mPresenter.loadInfoIsUserFollow(storeId, userId);
    }

    @Override
    public void displayHeader(Store store) {
        mAdapter.addItemStore(store);
    }

    @Override
    public void updateStatusFollow(Boolean isUserFollow) {
        //todo
    }

    @Override
    public void displayVoucherInStore(List<Voucher> voucherList, Boolean isClearData) {
        mAdapter.addItemVoucherInStore(voucherList, isClearData);
    }

    public void loadData() {
        mPresenter.loadInfoStore(mStoreId);
        mPresenter.loadVoucherInStore(mStoreId, true);
        mPresenter.loadInfoIsUserFollow(mStoreId, userId);

//        mUpdateData = true;
//        mHandler = new Handler();
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (mUpdateData && !mRecyclerDetailStore.isComputingLayout()) {
//                    mAdapter.notifyDataSetChanged();
//                    mHandler.removeCallbacks(mRunnable);
//                    mUpdateData = false;
//                }
//            }
//        };
//        mHandler.postDelayed(mRunnable, 0);
    }
}
