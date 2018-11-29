package kienpd.com.mtee.ui.voucher.save;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;

public class VoucherSavePresenter<V extends VoucherSaveMvpView> extends BasePresenter<V>
        implements VoucherSaveMvpPresenter<V> {

    @Override
    public void loadData(int userId, final Boolean isClearData) {
        final ApiRequest.ApiRequestVoucherSaveByUserId request = new ApiRequest.ApiRequestVoucherSaveByUserId(userId);
        API.getVoucherSaveByUserId(request, new API.APICallback<List<Voucher>>() {
            @Override
            public void onResponse(List<Voucher> response) throws JSONException {
                if (response != null && response.size() > 0) {
                    getMvpView().displayData(response, isClearData);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

