package kienpd.com.mtee.ui.home.code;

import kienpd.com.mtee.ui.base.MvpView;

public interface CodeMvpView extends MvpView {

    void displayView(String title, String urlVoucher, int countLike, String code, String nameStore, String addressStore, String dateVoucher, String nameUser, String phoneUser, String emailUser,String description,Long timeStart,Long timeEnd);

}
