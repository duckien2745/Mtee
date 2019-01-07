package kienpd.com.mtee.ui.home;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void showDetailDialog(int detailId);

    void showCollectionDialog(int collectionId,int categoryId);

    void loadCategory();

    void loadHighLightData(int categoryId,Boolean isClearData);

    void loadCollectionData(int categoryId,Boolean isClearData);

    void loadNewestData(int categoryId,Boolean isClearData);

}
