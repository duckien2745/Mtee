package kienpd.com.mtee.ui.home.active;

import android.util.Log;

import org.json.JSONException;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.ui.base.BasePresenter;

public class ActivePresenter<V extends ActiveMvpView> extends BasePresenter<V>
        implements ActiveMvpPresenter<V> {

    @Override
    public void activeCode(int password, String code) {
        ApiRequest.ApiRequestActiveCode activeCode = new ApiRequest.ApiRequestActiveCode(code, password);
        API.activeCode(activeCode, new API.APICallback<Messager>() {
            @Override
            public void onResponse(Messager response) throws JSONException {
                Log.d("gygbdued","111111"+response.toString());
                if (response != null) {
                    getMvpView().statusActiveVoucher(response);
                }
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d("gygbdued","2222222");

            }
        });
    }
}

