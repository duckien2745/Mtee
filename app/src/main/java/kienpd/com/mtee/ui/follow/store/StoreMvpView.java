package kienpd.com.mtee.ui.follow.store;

import java.util.List;

import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.MvpView;

public interface StoreMvpView extends MvpView {

    void displayHeader(Store store);

    void updateStatusFollow(Boolean isUserFollow);

    void displayStatusFollow(Boolean isUserFollow);

    void displayVoucherInStore(List<Voucher> voucherList, Boolean isClearData);

}
