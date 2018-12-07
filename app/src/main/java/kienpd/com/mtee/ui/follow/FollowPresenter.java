package kienpd.com.mtee.ui.follow;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
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

    @Override
    public void updateStatusUserFollow(final int storeId, int userId) {
        ApiRequest.ApiRequestStatusUserFollow request = new ApiRequest.ApiRequestStatusUserFollow(storeId, userId);
        API.updateStatusUserFollow(request, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null) {
                    getMvpView().updateStatusFollow(response.getStatus() == 1, storeId);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

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

