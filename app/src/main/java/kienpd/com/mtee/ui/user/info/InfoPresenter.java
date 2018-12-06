package kienpd.com.mtee.ui.user.info;

import org.json.JSONException;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.ui.base.BasePresenter;

public class InfoPresenter<V extends InfoMvpView> extends BasePresenter<V>
        implements InfoMvpPresenter<V> {

    @Override
    public void updateUserData(String name, Integer phone, long birthday, String gender) {
        ApiRequest.ApiRequestUpdateDataUser requestUpdateDataUser = new ApiRequest.ApiRequestUpdateDataUser(name, phone, birthday, gender);
        API.updateUserData(requestUpdateDataUser, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {

                if (response != null) {
                    getMvpView().updateData(response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

