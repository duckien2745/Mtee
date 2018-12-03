package kienpd.com.mtee.ui.follow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.ui.adapter.holder.FollowStoreAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.follow.store.StoreFragment;
import kienpd.com.mtee.utils.CommonUtils;

public class FollowFragment extends BaseFragment implements FollowMvpView, FollowStoreAdapter.StoreFollowAdapterCallback {

    FollowMvpPresenter<FollowMvpView> mPresenter;

    @BindView(R.id.recycler_store)
    RecyclerView mRecyclerStore;

    @BindView(R.id.process_loading)
    ProgressBar mProgressBar;

    private FollowStoreAdapter mAdapter;
    private List<Store> mList;
    private int userId = 37281321;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_store, container, false);
        setUnBinder(ButterKnife.bind(this, view));

        mPresenter = new FollowPresenter<>();
        mPresenter.onAttach(this);
        return view;
    }

    @Override
    protected void setUp(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerStore.setVisibility(View.GONE);

        int px = CommonUtils.dpToPx(10);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 1);

        mAdapter = new FollowStoreAdapter(getBaseActivity(), new ArrayList<Store>(), this);
        mRecyclerStore.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerStore.addItemDecoration(itemDecoration);
        mRecyclerStore.setAdapter(mAdapter);

        mPresenter.loadData(userId, true);
    }

    @Override
    public void onClickStoreFollow(int id) {
        StoreFragment fragment = StoreFragment.newInstance(id);
        fragment.show(getFragmentManager(), StoreFragment.TAG);
    }

    @Override
    public void onClickButtonFollowStore(int storeId) {
        mPresenter.updateStatusUserFollow(storeId, userId);
    }

    @Override
    public void displayData(List<Store> storeList, Boolean isClearData) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerStore.setVisibility(View.VISIBLE);

        mList = storeList;
        mAdapter.addItem(storeList, isClearData);
    }

    @Override
    public void updateStatusFollow(Boolean isUserFollow, int storeId) {
        if (!isUserFollow) {
            if (mList != null && mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getId() == storeId) {
                        removeAt(i);
                        break;
                    }
                }
            }
        }
    }

    public void removeAt(int position) {
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, mList.size());
    }

}
