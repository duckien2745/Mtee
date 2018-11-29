package kienpd.com.mtee.ui.voucher.taken;

import java.util.List;

import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.ui.base.MvpView;

public interface VoucherTakenMvpView extends MvpView {

    void displayData(List<UserCode> userCodes,Boolean isClearData);
}
