package kienpd.com.mtee.ui.home;

import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.MvpView;

public interface HomeMvpView extends MvpView {

    void showDetailDialog();

    void updateRepoHighLight(List<Voucher> repoList);

    void updateRepoNewest(List<Voucher> repoList);

    void updateRepoCollection(List<Collection> repoList);


}
