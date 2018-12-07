package kienpd.com.mtee.ui.voucher;

import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.MvpView;

public interface MyVoucherMvpView extends MvpView {

    void updateUI(User user);
}
