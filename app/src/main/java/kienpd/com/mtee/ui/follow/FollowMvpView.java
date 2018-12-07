package kienpd.com.mtee.ui.follow;

import java.util.List;

import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.MvpView;

public interface FollowMvpView extends MvpView {
    void displayData(List<Store> storeList, Boolean isClearData);

    void updateStatusFollow(Boolean isUserFollow,int storeId);

    void updateUI(User user);

}
