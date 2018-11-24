package kienpd.com.mtee.ui.home;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void showDetailDialog(int detailId);

    void loadHighLightData(int categoryId);

    void loadCollectionData(int categoryId);

    void loadNewestData(int categoryId);

}
