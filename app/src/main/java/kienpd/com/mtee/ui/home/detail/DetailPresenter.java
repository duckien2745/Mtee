package kienpd.com.mtee.ui.home.detail;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.StatusLikeSaveRating;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;

public class DetailPresenter<V extends DetailMvpView> extends BasePresenter<V>
        implements DetailMvpPresenter<V> {

    private Boolean mIsShow = false;


    @Override
    public void loadDetailData(int voucherId) {
        //todo
        final ApiRequest.ApiRequestVoucherById requestVoucher = new ApiRequest.ApiRequestVoucherById(voucherId);
        API.getVoucherById(requestVoucher, new API.APICallback<Voucher>() {
            @Override
            public void onResponse(Voucher response) throws JSONException {
                if (response != null) {
                    Store store = response.getStore();
                    Integer likeCount = response.getLikeCount();
                    List<String> listPicture = response.getPictures();
                    String title = response.getTitle();

                    Address address = response.getStore().getAddress();
                    String sAddress = "";
                    if (address != null) {
                        sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
                    }
                    List<String> listPricePicture = response.getPricePictures();
                    String description = response.getDescription();

                    RatingResponse rating = response.getRatingResponse();
                    int totalRate = rating.getRating1star() + rating.getRating2star() + rating.getRating3star() + rating.getRating4star() + rating.getRating5star();

                    //todo tinh trung binh,lam tron star
                    float star = 4;

                    getMvpView().displayDetailView(store.getName(), likeCount, listPicture, title, sAddress, listPricePicture, description, star, totalRate, rating);

                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void likeDetail(int userId, int detailId) {
        ApiRequest.ApiRequestStatus requestStatus = new ApiRequest.ApiRequestStatus(userId, detailId);
        API.updateLike(requestStatus, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null && response.getStatus() != null) {
                    getMvpView().updateLike(response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {
            }
        });
    }

    @Override
    public void saveDetail(int userId, int detailId) {
        ApiRequest.ApiRequestStatus requestStatus = new ApiRequest.ApiRequestStatus(userId, detailId);
        API.updateSave(requestStatus, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null && response.getStatus() != null) {
                    getMvpView().updateSave(response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void rattingDetail(int userId, int detailId, int rating) {
        //todo
    }

    @Override
    public void shareDetail(String title, String content) {
        //todo
    }

    @Override
    public void direct(String location) {
        //todo
    }

    @Override
    public void getStatusLikeSaveRateDetail(int userId, int detailId) {
        ApiRequest.ApiRequestStatus requestStatus = new ApiRequest.ApiRequestStatus(userId, detailId);
        API.getStatus(requestStatus, new API.APICallback<StatusLikeSaveRating>() {
            @Override
            public void onResponse(StatusLikeSaveRating response) throws JSONException {
                if (response != null) {
                    getMvpView().updateLike(response.getLike() == 1);
                    getMvpView().updateSave(response.getSave() == 1);
                    getMvpView().displayMyRating(response.getRating());
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void showTextMore() {
        mIsShow = !mIsShow;
        getMvpView().showTextDescription(mIsShow);
    }
}
