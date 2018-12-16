package kienpd.com.mtee.ui.voucher.taken;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.ui.base.BasePresenter;

public class VoucherTakenPresenter<V extends VoucherTakenMvpView> extends BasePresenter<V>
        implements VoucherTakenMvpPresenter<V> {


    @Override
    public void loadData(int userId, final Boolean isClearData) {

        ApiRequest.ApiRequestVoucherTakenByUserId request = new ApiRequest.ApiRequestVoucherTakenByUserId(userId);
        API.getVoucherTakenByUserId(request, new API.APICallback<List<UserCode>>() {
            @Override
            public void onResponse(List<UserCode> response) throws JSONException {
                if (response != null) {
                    getMvpView().displayData(response, isClearData);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

