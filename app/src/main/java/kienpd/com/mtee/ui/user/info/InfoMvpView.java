package kienpd.com.mtee.ui.user.info;

import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.MvpView;

public interface InfoMvpView extends MvpView {

    void displayInfo(User user);

    void updateData(Boolean isUpdate);

    void saveUserDataCache(User user);
}
