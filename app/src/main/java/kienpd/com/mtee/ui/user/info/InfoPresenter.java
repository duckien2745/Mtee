package kienpd.com.mtee.ui.user.info;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.ui.base.BasePresenter;
import kienpd.com.mtee.utils.TextUtil;

public class InfoPresenter<V extends InfoMvpView> extends BasePresenter<V>
        implements InfoMvpPresenter<V> {

    @Override
    public void updateUserData(String jsonUser, Integer userId, String name, Integer phone, long birthday, String gender) {
        ApiRequest.ApiRequestUpdateDataUser requestUpdateDataUser = new ApiRequest.ApiRequestUpdateDataUser();

        User user = null;
        if (jsonUser != null && !TextUtil.isEmpty(jsonUser)) {
            Gson gson = new Gson();
            user = gson.fromJson(jsonUser, User.class);
        }

        if (userId != null && userId != 0) {
            requestUpdateDataUser.setId(userId);
        }

        if (!TextUtil.isEmpty(name)) {
            requestUpdateDataUser.setName(name);
            if (user != null) {
                user.setName(name);
            }
        }
        if (phone != null && phone != 0) {
            requestUpdateDataUser.setPhone(phone);
            if (user != null) {
                user.setPhone(phone);
            }
        }
        if (birthday != 0) {
            requestUpdateDataUser.setBirthday(birthday);
            if (user != null) {
                user.setBirthday(birthday);
            }
        }
        if (!TextUtil.isEmpty(gender)) {
            requestUpdateDataUser.setGender(gender);
            if (user != null) {
                user.setGender(gender);
            }
        }
        final User finalUser = user;
        API.updateUserData(requestUpdateDataUser, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {

                if (response != null) {
                    if (response.getStatus() == 1) {
                        getMvpView().updateData(true);
                        getMvpView().saveUserDataCache(finalUser);
                    } else {
                        getMvpView().updateData(false);
                    }
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

