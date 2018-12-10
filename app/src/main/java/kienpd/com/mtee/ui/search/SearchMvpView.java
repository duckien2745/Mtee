package kienpd.com.mtee.ui.search;

import java.util.List;

import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.ui.base.MvpView;

public interface SearchMvpView extends MvpView {

    void updateRepoSearchView(List<Store> repoList, Boolean isClearData);

}
