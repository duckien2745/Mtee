package kienpd.com.mtee.ui.follow;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface FollowMvpPresenter<V extends FollowMvpView> extends MvpPresenter<V> {

    void loadData(int userId, Boolean isClearData);
}
