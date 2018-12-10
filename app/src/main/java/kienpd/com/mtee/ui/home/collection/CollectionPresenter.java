package kienpd.com.mtee.ui.home.collection;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;

public class CollectionPresenter<V extends CollectionMvpView> extends BasePresenter<V>
        implements CollectionMvpPresenter<V> {

    @Override
    public void loadDataByCollectionId(int collectionId, int categoryId, final Boolean isClearData) {
        ApiRequest.ApiRequestDetailCollection requestDetailCollection = new ApiRequest.ApiRequestDetailCollection(categoryId, collectionId);
        API.getVoucherByCollectionId(requestDetailCollection, new API.APICallback<List<Voucher>>() {
            @Override
            public void onResponse(List<Voucher> response) throws JSONException {
                if (response != null) {
                    getMvpView().updateRepoDetailCollection(response, isClearData);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

