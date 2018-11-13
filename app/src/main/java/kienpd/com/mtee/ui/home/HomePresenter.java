package kienpd.com.mtee.ui.home;

import kienpd.com.mtee.ui.base.BasePresenter;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V>
        implements HomeMvpPresenter<V> {

    @Override
    public void showDetailDialog() {
        getMvpView().showDetailDialog();
    }
}
