package kienpd.com.mtee.ui.main;

import android.os.Bundle;

import kienpd.com.mtee.R;
import kienpd.com.mtee.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    MainMvpPresenter<MainMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    @Override
    protected void setUp() {
        mPresenter = new MainPresenter<>();

        mPresenter.onAttach(this);
        mPresenter.onFacebookLoginClick();
    }


    @Override
    public void openMainActivity() {

    }
}
