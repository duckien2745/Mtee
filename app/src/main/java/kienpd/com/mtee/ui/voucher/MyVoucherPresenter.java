package kienpd.com.mtee.ui.voucher;

import android.util.Log;

import org.json.JSONException;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.BasePresenter;

public class MyVoucherPresenter<V extends MyVoucherMvpView> extends BasePresenter<V>
        implements MyVoucherMvpPresenter<V> {

    @Override
    public void signInWithGoogle(String type, String idToken) {
        ApiRequest.ApiRequestLogin requestLogin = new ApiRequest.ApiRequestLogin(idToken, type);
        API.login(requestLogin, new API.APICallback<User>() {
            @Override
            public void onResponse(User response) throws JSONException {
                if (response != null) {
                    getMvpView().updateUI(response);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

