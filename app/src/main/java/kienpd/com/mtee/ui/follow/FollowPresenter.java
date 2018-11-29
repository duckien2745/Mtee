package kienpd.com.mtee.ui.follow;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.ui.base.BasePresenter;

public class FollowPresenter<V extends FollowMvpView> extends BasePresenter<V>
        implements FollowMvpPresenter<V> {

    @Override
    public void loadData(int userId, final Boolean isClearData) {

        ApiRequest.ApiRequestStoreFollow requestGetCode = new ApiRequest.ApiRequestStoreFollow(userId);
        API.getStoreFollow(requestGetCode, new API.APICallback<List<Store>>() {
            @Override
            public void onResponse(List<Store> response) throws JSONException {
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

