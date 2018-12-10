package kienpd.com.mtee.ui.search;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {

    void loadData(String keyWord,Boolean isClearData);
}
