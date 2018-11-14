package kienpd.com.mtee.ui.home.voucher;

import android.view.View;

import kienpd.com.mtee.ui.base.BaseDialog;
import kienpd.com.mtee.ui.home.detail.DetailMvpView;

public class VoucherFragment extends BaseDialog implements VoucherMvpView{

    VoucherMvpPresenter<DetailMvpView> mPresenter;

    @Override
    protected void setUp(View view) {

    }
}
