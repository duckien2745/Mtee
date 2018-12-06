package kienpd.com.mtee.ui.user;

import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.MvpView;

public interface UserMvpView extends MvpView {

    void updateUI(User user);
}
