package kienpd.com.mtee.ui.home;

import java.util.List;

import kienpd.com.mtee.data.model.Category;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.MvpView;

public interface HomeMvpView extends MvpView {

    void showDetailDialog(int detailId);

    void showCollectionDialog(int collectionId, int categoryId);

    void updateRepoHighLight(List<Voucher> repoList, Boolean isClearData);

    void updateRepoNewest(List<Voucher> repoList, Boolean isClearData);

    void updateRepoCollection(List<Collection> repoList, Boolean isClearData);

    void updateRepoCategory(List<Category> repoList);


}
