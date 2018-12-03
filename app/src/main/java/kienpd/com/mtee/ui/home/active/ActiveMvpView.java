package kienpd.com.mtee.ui.home.active;

import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.ui.base.MvpView;

public interface ActiveMvpView extends MvpView {

    void statusActiveVoucher(Messager message);
}
