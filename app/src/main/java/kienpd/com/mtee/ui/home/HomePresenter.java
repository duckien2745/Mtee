package kienpd.com.mtee.ui.home;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;
import kienpd.com.mtee.utils.Const;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V>
        implements HomeMvpPresenter<V> {

    @Override
    public void showDetailDialog(int detailId) {
        getMvpView().showDetailDialog(detailId);
    }

    @Override
    public void loadHighLightData(int categoryId, final Boolean isClearData) {
        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?
        ApiRequest.ApiRequestVoucher param;
        if (categoryId != Const.Category.CATEGORY_ALL) {
            param = new ApiRequest.ApiRequestVoucher(0, 10, categoryId);
        } else {
            param = new ApiRequest.ApiRequestVoucher(0, 10);
        }
        API.getListVoucherHighLight(param, new API.APICallback<List<Voucher>>() {
            @Override
            public void onResponse(List<Voucher> response) throws JSONException {
                getMvpView().updateRepoHighLight(response, isClearData);
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void loadCollectionData(int categoryId, final Boolean isClearData) {
        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        ApiRequest.ApiRequestCollection param;
        if (categoryId != Const.Category.CATEGORY_ALL) {
            param = new ApiRequest.ApiRequestCollection(0, 10, categoryId);
        } else {
            param = new ApiRequest.ApiRequestCollection(0, 10);
        }

        API.getListCollection(param, new API.APICallback<List<Collection>>() {
            @Override
            public void onResponse(List<Collection> response) throws JSONException {
                getMvpView().updateRepoCollection(response, isClearData);
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });
    }

    @Override
    public void loadNewestData(int categoryId, final Boolean isClearData) {
        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?
        ApiRequest.ApiRequestVoucher param;
        if (categoryId != Const.Category.CATEGORY_ALL) {
            param = new ApiRequest.ApiRequestVoucher(0, 20, categoryId);
        } else {
            param = new ApiRequest.ApiRequestVoucher(0, 20);
        }
        API.getListVoucherNewest(param, new API.APICallback<List<Voucher>>() {
            @Override
            public void onResponse(List<Voucher> response) throws JSONException {
                getMvpView().updateRepoNewest(response, isClearData);
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }


}
