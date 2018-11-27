package kienpd.com.mtee.ui.home.collection;

import java.util.List;

import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.MvpView;

public interface CollectionMvpView extends MvpView {
    void updateRepoDetailCollection(List<Voucher> voucherList,Boolean isClearData);
}
