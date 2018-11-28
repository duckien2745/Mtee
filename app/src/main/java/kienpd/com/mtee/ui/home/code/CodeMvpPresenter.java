package kienpd.com.mtee.ui.home.code;

import android.content.Context;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface CodeMvpPresenter<V extends CodeMvpView> extends MvpPresenter<V> {

    void getInfoVoucherUser(int detailId, int userId);

    void shareVoucher(Context context, String title, String content);

    void copyCode(Context context,String code);

    void activeVoucher();

}
