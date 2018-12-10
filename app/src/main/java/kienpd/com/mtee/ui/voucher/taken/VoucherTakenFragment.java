package kienpd.com.mtee.ui.voucher.taken;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.db.StorageManager;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.VoucherTakenAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.follow.store.StoreFragment;
import kienpd.com.mtee.ui.home.HomeMvpPresenter;
import kienpd.com.mtee.ui.home.HomeMvpView;
import kienpd.com.mtee.ui.home.HomePresenter;
import kienpd.com.mtee.ui.home.code.CodeFragment;
import kienpd.com.mtee.utils.CommonUtils;
import kienpd.com.mtee.utils.Const;
import kienpd.com.mtee.utils.TextUtil;

public class VoucherTakenFragment extends BaseFragment implements VoucherTakenMvpView, VoucherTakenAdapter.VoucherTakenAdapterCallback {

    VoucherTakenMvpPresenter<VoucherTakenMvpView> mPresenter;

    @BindView(R.id.recycler_voucher_taken)
    RecyclerView mRecyclerVoucherTaken;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    @BindView(R.id.layout_refresh)
    LinearLayout mLayoutRefresh;

    @BindView(R.id.text_tab_to_refresh)
    TextView mTextRefresh;

    private VoucherTakenAdapter mAdapter;
    private int userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher_taken, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        mPresenter = new VoucherTakenPresenter<>();
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

                mAdapter = new VoucherTakenAdapter(getBaseActivity(), new ArrayList<UserCode>(), this);
                mRecyclerVoucherTaken.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
                mRecyclerVoucherTaken.addItemDecoration(itemDecoration);
                mRecyclerVoucherTaken.setAdapter(mAdapter);

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
    public void onClickVoucherTakenListener(UserCode userCode) {

        if (userCode != null) {
            Gson gson = new Gson();
            String jsonUserCode = gson.toJson(userCode);
            CodeFragment fragment = CodeFragment.newInstance(-111, jsonUserCode);
            fragment.show(getFragmentManager(), CodeFragment.TAG);
        }
    }

    @Override
    public void onClickVoucherFollow(int id) {

    }

    @Override
    public void onClickStoreVoucher(int id) {
        StoreFragment fragment = StoreFragment.newInstance(id);
        fragment.show(getFragmentManager(), StoreFragment.TAG);
    }

    @Override
    public void displayData(List<UserCode> userCodes, Boolean isClearData) {
        mSwipeToRefresh.setRefreshing(false);

        if (userCodes.size() > 0) {
            mRecyclerVoucherTaken.setVisibility(View.VISIBLE);
            mLayoutRefresh.setVisibility(View.GONE);
            mAdapter.addItem(userCodes, isClearData);
        } else {
            mRecyclerVoucherTaken.setVisibility(View.GONE);
            mLayoutRefresh.setVisibility(View.VISIBLE);
        }

    }
}
