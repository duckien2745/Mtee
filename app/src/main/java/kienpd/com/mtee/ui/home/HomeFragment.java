package kienpd.com.mtee.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.HomeAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.home.collection.CollectionFragment;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.Const;

public class HomeFragment extends BaseFragment implements HomeMvpView, HomeAdapter.HomeAdapterCallBack {

    HomeMvpPresenter<HomeMvpView> mPresenter;

    @BindView(R.id.recycler_home)
    RecyclerView mRecyclerViewHome;

    private HomeAdapter mAdapter;
    private int mCategoryId = Const.Category.CATEGORY_ALL;

    private Handler mHandler;
    private Runnable mRunnable;
    private Boolean mUpdateData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new HomePresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        GridLayoutManager manager = new GridLayoutManager(getBaseActivity(), 2);
        mAdapter = new HomeAdapter(getBaseActivity(), new ArrayList<Voucher>(), new ArrayList<Voucher>(), new ArrayList<Collection>(), this);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == HomeAdapter.TYPE_ITEM_NEWEST ? 1 : 2;
            }
        });

        mRecyclerViewHome.setLayoutManager(manager);
        mRecyclerViewHome.setAdapter(mAdapter);

        loadData(mCategoryId, true);
    }

    @Override
    public void onClickListener(int type, int id) {
        switch (type) {
            case 0:
                break;
            case 1:
                mPresenter.showDetailDialog(id);
                break;
            case 2:
                mPresenter.showCollectionDialog(id, 0);
                break;
            case 3:
                mPresenter.showDetailDialog(id);
                break;
            default:
                break;
        }

    }

    @Override
    public void onClickCategoryListener(int categoryId) {
        mCategoryId = categoryId;
        loadData(categoryId, true);
    }

    @Override
    public void onClickLoadMore() {
        mPresenter.loadNewestData(mCategoryId, false);
    }


    @Override
    public void showDetailDialog(int detailId) {
        VoucherFragment fragment = VoucherFragment.newInstance(detailId);
        fragment.show(getFragmentManager(), VoucherFragment.TAG);
    }

    @Override
    public void showCollectionDialog(int collectionId, int categoryID) {
        CollectionFragment fragment = CollectionFragment.newInstance(collectionId, categoryID);
        fragment.show(getFragmentManager(), CollectionFragment.TAG);
    }

    @Override
    public void updateRepoHighLight(List<Voucher> repoList, Boolean isClearData) {
        mAdapter.addItemHighLight(repoList, isClearData);
    }

    @Override
    public void updateRepoNewest(List<Voucher> repoList, Boolean isClearData) {
        mAdapter.addItemNewest(repoList, isClearData);
    }

    @Override
    public void updateRepoCollection(List<Collection> repoList, Boolean isClearData) {
        mAdapter.addItemCollection(repoList, isClearData);
    }

    public void loadData(int categoryId, Boolean isClearData) {
        mPresenter.loadHighLightData(categoryId, isClearData);
        mPresenter.loadCollectionData(categoryId, isClearData);
        mPresenter.loadNewestData(categoryId, isClearData);

        mUpdateData = true;
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mUpdateData && !mRecyclerViewHome.isComputingLayout()) {
                    mAdapter.notifyDataSetChanged();
                    mHandler.removeCallbacks(mRunnable);
                    mUpdateData = false;
                }
            }
        };
        mHandler.postDelayed(mRunnable, 0);
    }
}


