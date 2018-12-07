package kienpd.com.mtee.ui.follow;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface FollowMvpPresenter<V extends FollowMvpView> extends MvpPresenter<V> {

    void loadData(int userId, Boolean isClearData);

    void updateStatusUserFollow(int storeId, int userId);

    void signInWithGoogle(String type, String idToken);

}
