

package kienpd.com.mtee.ui.main;

import kienpd.com.mtee.ui.base.BasePresenter;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static final String TAG = "MainPresenter";


    @Override
    public void onServerLoginClick(String email, String password) {

    }

    @Override
    public void onGoogleLoginClick() {

    }

    @Override
    public void onFacebookLoginClick() {

    }
}
