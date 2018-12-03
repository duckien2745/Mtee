package kienpd.com.mtee.ui.voucher.save;

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
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.VoucherSaveAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.custom.GridDividerItemDecoration;
import kienpd.com.mtee.ui.follow.store.StoreFragment;
import kienpd.com.mtee.ui.home.detail.VoucherFragment;
import kienpd.com.mtee.utils.CommonUtils;

public class VoucherSaveFragment extends BaseFragment implements VoucherSaveMvpView, VoucherSaveAdapter.VoucherSaveAdapterCallback {

    VoucherSaveMvpPresenter<VoucherSaveMvpView> mPresenter;

    @BindView(R.id.recycler_voucher_save)
    RecyclerView mRecyclerVoucherSave;

    @BindView(R.id.process_loading)
    ProgressBar mProgressBar;

    private VoucherSaveAdapter mAdapter;
    private int userId = 37281321;

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
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerVoucherSave.setVisibility(View.GONE);

        int px = CommonUtils.dpToPx(10);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(px, 1);

        mAdapter = new VoucherSaveAdapter(getBaseActivity(), new ArrayList<Voucher>(), this);
        mRecyclerVoucherSave.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerVoucherSave.addItemDecoration(itemDecoration);
        mRecyclerVoucherSave.setAdapter(mAdapter);
        mPresenter.loadData(userId, true);
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
        mProgressBar.setVisibility(View.GONE);
        mRecyclerVoucherSave.setVisibility(View.VISIBLE);

        mAdapter.addItem(voucherList, isClearData);
    }
}
