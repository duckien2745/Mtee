package kienpd.com.mtee.ui.voucher.taken;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kienpd.com.mtee.R;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.adapter.VoucherTakenAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.home.HomeMvpPresenter;
import kienpd.com.mtee.ui.home.HomeMvpView;
import kienpd.com.mtee.ui.home.HomePresenter;

public class VoucherTakenFragment extends BaseFragment implements VoucherTakenMvpView, VoucherTakenAdapter.VoucherTakenAdapterCallback {

    VoucherTakenMvpPresenter<VoucherTakenMvpView> mPresenter;

    @BindView(R.id.recycler_voucher_taken)
    RecyclerView mRecyclerVoucherTaken;

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
        VoucherTakenAdapter adapter = new VoucherTakenAdapter(getBaseActivity(), new ArrayList<UserCode>(), this);
        mRecyclerVoucherTaken.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerVoucherTaken.setAdapter(adapter);
    }

    @Override
    public void onClickVoucherTakenListener(int id) {

    }

    @Override
    public void onClickVoucherFollow(int id) {

    }
}
