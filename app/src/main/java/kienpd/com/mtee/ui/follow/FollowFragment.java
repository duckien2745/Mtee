package kienpd.com.mtee.ui.follow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private FollowStoreAdapter mAdapter;
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
    public void displayData(List<Store> storeList, Boolean isClearData) {
        mAdapter.addItem(storeList, isClearData);
    }
}
