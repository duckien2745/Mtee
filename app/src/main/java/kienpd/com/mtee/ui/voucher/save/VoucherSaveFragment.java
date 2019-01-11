package kienpd.com.mtee.ui.voucher.save;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.VoucherSaveAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.follow.store.StoreFragment;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

public class VoucherSaveFragment extends BaseFragment implements VoucherSaveMvpView, VoucherSaveAdapter.VoucherSaveAdapterCallback {

    VoucherSaveMvpPresenter<VoucherSaveMvpView> mPresenter;

    @BindView(R.id.recycler_voucher_save)
    RecyclerView mRecyclerVoucherSave;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    @BindView(R.id.layout_refresh)
    LinearLayout mLayoutRefresh;

    @BindView(R.id.text_tab_to_refresh)
    TextView mTextRefresh;

    private VoucherSaveAdapter mAdapter;
    private int userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher_save, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new VoucherSavePresenter<>();
        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        String jsonUser = StorageManager.getStringValue(getBaseActivity(), Const.User.KEY_SAVE_USER);
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonUser, User.class);
            if (user != null) {
                userId = user.getId();
                mSwipeToRefresh.setColorSchemeResources(R.color.color_item_select);

                int px = CommonUtils.dpToPx(10);
                GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 1);

                mAdapter = new VoucherSaveAdapter(getBaseActivity(), new ArrayList<Voucher>(), this);
                mRecyclerVoucherSave.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
                mRecyclerVoucherSave.addItemDecoration(itemDecoration);
                mRecyclerVoucherSave.setAdapter(mAdapter);

                mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.loadData(userId, true);
                    }
                });

                mTextRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSwipeToRefresh.setRefreshing(true);
                        mPresenter.loadData(userId, true);
                    }
                });

                mSwipeToRefresh.setRefreshing(true);
                mPresenter.loadData(userId, true);
            }

        }
    }

    @Override
    public void onClickVoucherSaveListener(int id) {
        VoucherFragment fragment = VoucherFragment.newInstance(id);
        fragment.show(getFragmentManager(), VoucherFragment.TAG);
    }

    @Override
    public void onClickVoucherFollowListener(int id) {

    }

    @Override
    public void onClickStoreVoucher(int id) {
        StoreFragment fragment = StoreFragment.newInstance(id);
        fragment.show(getFragmentManager(), StoreFragment.TAG);
    }

    @Override
    public void displayData(List<Voucher> voucherList, Boolean isClearData) {
        mSwipeToRefresh.setRefreshing(false);

        if (voucherList.size() > 0) {
            mRecyclerVoucherSave.setVisibility(View.VISIBLE);
            mLayoutRefresh.setVisibility(View.GONE);
            mAdapter.addItem(voucherList, isClearData);
        } else {
            mRecyclerVoucherSave.setVisibility(View.GONE);
            mLayoutRefresh.setVisibility(View.VISIBLE);
        }

    }
}
