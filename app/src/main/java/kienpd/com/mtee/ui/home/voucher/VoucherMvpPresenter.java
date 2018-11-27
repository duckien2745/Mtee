package kienpd.com.mtee.ui.home.voucher;

import android.content.Context;

import kienpd.com.mtee.ui.base.MvpPresenter;
import kienpd.com.mtee.ui.home.rules.RulesMvpView;

public interface VoucherMvpPresenter<V extends VoucherMvpView> extends MvpPresenter<V> {

    void getInfoVoucherUser(int detailId, int userId);

    void shareVoucher(Context context, String title, String content);

    void showDescriptionVoucher(int detailId);

    void showDetailVoucher(int detailId);

    void copyCode(String code);

    void activeVoucher();

}
