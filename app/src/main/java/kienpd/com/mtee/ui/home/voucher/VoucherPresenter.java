package kienpd.com.mtee.ui.home.voucher;

import android.content.Context;
import android.content.Intent;

import kienpd.com.mtee.ui.base.BasePresenter;
import kienpd.com.mtee.ui.home.rules.RulesMvpPresenter;
import kienpd.com.mtee.ui.home.rules.RulesMvpView;

public class VoucherPresenter<V extends VoucherMvpView> extends BasePresenter<V>
        implements VoucherMvpPresenter<V> {

    @Override
    public void getInfoVoucherUser(int detailId, int userId) {
        //todo
    }

    @Override
    public void shareVoucher(Context context, String title, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, title));
    }

    @Override
    public void showDescriptionVoucher(int detailId) {
        //todo
    }

    @Override
    public void showDetailVoucher(int detailId) {
        //todo
    }

    @Override
    public void copyCode(int code) {
        //todo
    }

    @Override
    public void activeVoucher() {
        //todo
    }
}

