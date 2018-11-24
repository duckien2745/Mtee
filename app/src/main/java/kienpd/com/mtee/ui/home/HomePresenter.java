package kienpd.com.mtee.ui.home;

import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.APIDefinition;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V>
        implements HomeMvpPresenter<V> {

    @Override
    public void showDetailDialog(int detailId) {
        getMvpView().showDetailDialog(detailId);
    }

    @Override
    public void loadHighLightData(int categoryId) {
        //Get data
        final List<Voucher> voucherList = new ArrayList<>();

        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        ApiRequest.ApiRequestVoucher param = new ApiRequest.ApiRequestVoucher(0, 10);
        API.getListVoucherhighLight(param, new API.APICallback<List<Voucher>>() {
            @Override
            public void onResponse(List<Voucher> response) throws JSONException {
                getMvpView().updateRepoHighLight(response);
                Log.d("ygebdyebd",response.toString());
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d("ygebdyebd",message);

            }
        });

        getMvpView().updateRepoHighLight(voucherList);
    }

    @Override
    public void loadCollectionData(int categoryId) {
        //Get data
        List<Collection> collectionList = new ArrayList<>();

        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        getMvpView().updateRepoCollection(collectionList);
    }

    @Override
    public void loadNewestData(int categoryId) {
        //Get data
        ArrayList<Voucher> voucherList = new ArrayList<>();

        //todo
        //check  categoryId = Const.Category.CATEGORY_ALL ?

        getMvpView().updateRepoNewest(voucherList);
    }


}
