package kienpd.com.mtee.ui.voucher.save;

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
import kienpd.com.mtee.ui.adapter.VoucherSaveAdapter;
import kienpd.com.mtee.ui.adapter.VoucherTakenAdapter;
import kienpd.com.mtee.ui.base.BaseFragment;
import kienpd.com.mtee.ui.voucher.taken.VoucherTakenMvpPresenter;
import kienpd.com.mtee.ui.voucher.taken.VoucherTakenMvpView;
import kienpd.com.mtee.ui.voucher.taken.VoucherTakenPresenter;

public class VoucherSaveFragment extends BaseFragment implements VoucherSaveMvpView, VoucherSaveAdapter.VoucherSaveAdapterCallback {

    VoucherSaveMvpPresenter<VoucherSaveMvpView> mPresenter;

    @BindView(R.id.recycler_voucher_save)
    RecyclerView mRecyclerVoucherSave;

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
        VoucherSaveAdapter adapter = new VoucherSaveAdapter(getBaseActivity(), new ArrayList<Voucher>(), this);
        mRecyclerVoucherSave.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerVoucherSave.setAdapter(adapter);
    }


    @Override
    public void onClickVoucherSaveListener(int id) {

    }

    @Override
    public void onClickVoucherFollowListener(int id) {

    }
}
