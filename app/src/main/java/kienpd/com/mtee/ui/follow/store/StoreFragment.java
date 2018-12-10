package kienpd.com.mtee.ui.follow.store;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.DetailStoreAdapter;
import kienpd.com.mtee.ui.base.BaseActivity;
import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

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

    @BindView(R.id.process_loading)
    ProgressBar mProgressBar;

    @BindView(R.id.image_back)
    ImageView mImageBack;

    @BindView(R.id.text_title_center)
    TextView mTextTitleCenter;

    private int mStoreId;
    private int userId = 0;
    private Boolean mIsUserFollow = true;

    private StoreListener mStoreListener;

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
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerDetailStore.setVisibility(View.GONE);

        mAdapter = new DetailStoreAdapter(getBaseActivity(), new ArrayList<Voucher>(), null, this);
        GridLayoutManager manager = new GridLayoutManager(getBaseActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == DetailStoreAdapter.TYPE_ITEM_VOUCHER_STORE ? 1 : 2;
            }
        });

        mRecyclerDetailStore.setLayoutManager(manager);
        mRecyclerDetailStore.setAdapter(mAdapter);

        mStoreId = getArguments().getInt(EXTRAS_STORE_ID);
        String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            if (user != null) {
                userId = user.getId();
                loadData();
            }
        }

        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStoreListener != null) {
                    mStoreListener.onUpdateFollow(mStoreId, mIsUserFollow);
                }
                dismissDialog(TAG);
            }
        });
        mTextTitleCenter.setText("Cửa hàng");
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
        mPresenter.updateStatusUserFollow(storeId, userId);
    }

    @Override
    public void displayHeader(Store store) {
        mAdapter.addItemStore(store);
    }

    @Override
    public void updateStatusFollow(Boolean isUserFollow) {
        mIsUserFollow = isUserFollow;
        mAdapter.updateStatusFollow(isUserFollow);
    }

    @Override
    public void displayStatusFollow(final Boolean isUserFollow) {
        mUpdateData = true;
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mUpdateData && !mRecyclerDetailStore.isComputingLayout()) {
                    mAdapter.displayStatusFollow(isUserFollow);
                    mHandler.removeCallbacks(mRunnable);
                    mUpdateData = false;
                }
            }
        };
        mHandler.postDelayed(mRunnable, 500);
    }

    @Override
    public void displayVoucherInStore(List<Voucher> voucherList, Boolean isClearData) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerDetailStore.setVisibility(View.VISIBLE);

        mAdapter.addItemVoucherInStore(voucherList, isClearData);
    }

    public void loadData() {
        mPresenter.loadInfoStore(mStoreId);
        mPresenter.loadVoucherInStore(mStoreId, true);
        mPresenter.getStatusUserFollow(mStoreId, userId);
    }

    public interface StoreListener {
        void onUpdateFollow(int id, Boolean isFollow);
    }

    public void setStoreListener(StoreListener mStoreListener) {
        this.mStoreListener = mStoreListener;
    }


}
