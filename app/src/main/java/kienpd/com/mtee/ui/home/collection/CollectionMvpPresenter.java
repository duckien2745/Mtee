package kienpd.com.mtee.ui.home.collection;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface CollectionMvpPresenter<V extends CollectionMvpView> extends MvpPresenter<V> {

    void loadDataByCollectionId(int collectionId,int categoryId,Boolean isClearData);

}
