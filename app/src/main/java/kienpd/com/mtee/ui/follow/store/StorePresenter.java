package kienpd.com.mtee.ui.follow.store;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;

public class StorePresenter<V extends StoreMvpView> extends BasePresenter<V>
        implements StoreMvpPresenter<V> {

    @Override
    public void loadVoucherInStore(int storeId, final Boolean isClearData) {
        ApiRequest.ApiRequestStoreById request = new ApiRequest.ApiRequestStoreById(storeId);
        API.getVoucherByStoreId(request, new API.APICallback<List<Voucher>>() {
            @Override
            public void onResponse(List<Voucher> response) throws JSONException {
                if (response != null && response.size() > 0) {
                    getMvpView().displayVoucherInStore(response, isClearData);
                }
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });
    }

    @Override
    public void loadInfoStore(final int storeId) {
        ApiRequest.ApiRequestStoreById request = new ApiRequest.ApiRequestStoreById(storeId);
        API.getStoreById(request, new API.APICallback<Store>() {
            @Override
            public void onResponse(Store response) throws JSONException {
                if (response != null) {
                    getMvpView().displayHeader(response);
                }
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });
    }

    @Override
    public void updateStatusUserFollow(int storeId, int userId) {
        ApiRequest.ApiRequestStatusUserFollow request = new ApiRequest.ApiRequestStatusUserFollow(storeId, userId);
        API.updateStatusUserFollow(request, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null) {
                    getMvpView().updateStatusFollow(response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });

    }

    @Override
    public void getStatusUserFollow(int storeId, int userId) {
        ApiRequest.ApiRequestStatusUserFollow request = new ApiRequest.ApiRequestStatusUserFollow(storeId, userId);
        API.getStatusUserFollow(request, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null) {
                    getMvpView().displayStatusFollow(
                            response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

