package kienpd.com.mtee.ui.follow.store;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface StoreMvpPresenter<V extends StoreMvpView> extends MvpPresenter<V> {

    void loadVoucherInStore(int storeId, Boolean isClearData);

    void loadInfoStore(int storeId);

    void updateStatusUserFollow(int storeId, int userId);

    void getStatusUserFollow(int storeId, int userId);
}
