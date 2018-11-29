package kienpd.com.mtee.ui.voucher.save;

import java.util.List;

import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.MvpView;

public interface VoucherSaveMvpView extends MvpView {

    void displayData(List<Voucher> voucherList, Boolean isClearData);
}
