package kienpd.com.mtee.ui.home.detail.comment;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Rating;
import kienpd.com.mtee.ui.base.BasePresenter;

public class CommentPresenter<V extends CommentMvpView> extends BasePresenter<V>
        implements CommentMvpPresenter<V> {

    @Override
    public void loadComment(int voucherId, final Boolean isClearData) {
        ApiRequest.ApiRequestEvaluation request = new ApiRequest.ApiRequestEvaluation(voucherId, 0, 10);
        API.loadEvaluation(request, new API.APICallback<List<Rating>>() {
            @Override
            public void onResponse(List<Rating> response) throws JSONException {
                if (response != null && response.size() > 0) {
                    getMvpView().displayComment(response, isClearData);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

