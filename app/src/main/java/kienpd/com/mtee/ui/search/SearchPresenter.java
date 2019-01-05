package kienpd.com.mtee.ui.search;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.ui.base.BasePresenter;

public class SearchPresenter<V extends SearchMvpView> extends BasePresenter<V>
        implements SearchMvpPresenter<V> {

    @Override
    public void loadData(String keyWord, final Boolean isClearData) {
        ApiRequest.ApiRequestSearchStore requestSearchStore = new ApiRequest.ApiRequestSearchStore(keyWord);
        API.searchStore(requestSearchStore, new API.APICallback<List<Store>>() {
            @Override
            public void onResponse(List<Store> response) throws JSONException {
                if (response != null) {
                    getMvpView().updateRepoSearchView(response, isClearData);
                }
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });

    }
}

