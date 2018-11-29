package kienpd.com.mtee.ui.voucher.save;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface VoucherSaveMvpPresenter<V extends VoucherSaveMvpView> extends MvpPresenter<V> {

    void loadData(int userId, Boolean isClearData);
}
