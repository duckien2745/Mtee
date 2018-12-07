package kienpd.com.mtee.ui.user.info;

import kienpd.com.mtee.ui.base.MvpPresenter;

public interface InfoMvpPresenter<V extends InfoMvpView> extends MvpPresenter<V> {

    void updateUserData(String jsonUser,Integer userId,String name,Integer phone,long birthday,String gender);

}
