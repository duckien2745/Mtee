package kienpd.com.mtee.ui.home.active;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface ActiveMvpPresenter<V extends ActiveMvpView> extends MvpPresenter<V> {

    void activeCode(int password,String code);

}
