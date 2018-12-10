package kienpd.com.mtee.ui.home.detail;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.StatusLikeSaveRating;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;
import kienpd.com.mtee.utils.TextUtil;

public class VoucherPresenter<V extends VoucherMvpView> extends BasePresenter<V>
        implements VoucherMvpPresenter<V> {

    private Boolean mIsShow = false;

    @Override
    public void loadInfoUser(final int userId) {
        ApiRequest.ApiRequestUser requestUser = new ApiRequest.ApiRequestUser(userId);
        API.getInfoUser(requestUser, new API.APICallback<User>() {
            @Override
            public void onResponse(User response) throws JSONException {
                if (response != null) {
                    if (!TextUtil.isEmpty(response.getName()) && !TextUtil.isEmpty(response.getPicture())) {
                        getMvpView().displayInfoUser(response.getName(), response.getPicture());
                    }
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void loadDetailData(int voucherId) {
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
                        sAddress = address.getNo() + " " + address.getStreet() + ", " + address.getDistrict() + ", " + address.getCity();
                    }
                    List<String> listPricePicture = response.getPricePictures();
                    String description = response.getDescription();

                    RatingResponse totalRatting = response.getRatingResponse();
                    getMvpView().displayTotalRatting(totalRatting);
                    getMvpView().displayDetailView(store, likeCount, listPicture, title, sAddress, listPricePicture, description);

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
    public void rattingDetail(int userId, final int detailId, float rating) {
        int star = Math.round(rating);
        ApiRequest.ApiRequestRating requestRating = new ApiRequest.ApiRequestRating(userId, detailId, star);
        API.rateVoucher(requestRating, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null) {
                    getMvpView().changeMyRatting(response.getStatus() == 1);
                    getTotalRatting(detailId);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void shareDetail(String title, String content) {
        //todo
    }


    @Override
    public void getStatusLikeSaveRateDetail(int userId, int detailId) {
        ApiRequest.ApiRequestStatus requestStatus = new ApiRequest.ApiRequestStatus(userId, detailId);
        API.getStatus(requestStatus, new API.APICallback<StatusLikeSaveRating>() {
            @Override
            public void onResponse(StatusLikeSaveRating response) throws JSONException {
                if (response != null) {
                    getMvpView().getStatusLike(response.getLike() == 1);
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

    @Override
    public void checkGetCodeInDay(int userId, int voucherId) {
        ApiRequest.ApiRequestCheckCode requestCheckCode = new ApiRequest.ApiRequestCheckCode(userId, voucherId);
        API.checkCodeIsNoTaken(requestCheckCode, new API.APICallback<Messager>() {
            @Override
            public void onResponse(Messager response) throws JSONException {
                if (response != null) {
                    getMvpView().statusGetCodeInDay(response);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void updateStatusUserFollow(int storeId, int userId) {
        ApiRequest.ApiRequestStatusUserFollow request = new ApiRequest.ApiRequestStatusUserFollow(storeId, userId);
        API.updateStatusUserFollow(request, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null) {
                    getMvpView().updateStatusFollow(response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void getStatusUserFollow(int storeId, int userId) {
        ApiRequest.ApiRequestStatusUserFollow request = new ApiRequest.ApiRequestStatusUserFollow(storeId, userId);
        API.getStatusUserFollow(request, new API.APICallback<Message>() {
            @Override
            public void onResponse(Message response) throws JSONException {
                if (response != null) {
                    getMvpView().displayStatusFollow(
                            response.getStatus() == 1);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void signInWithGoogle(String type, String idToken, final int action) {
        ApiRequest.ApiRequestLogin requestLogin = new ApiRequest.ApiRequestLogin(idToken, type);
        API.login(requestLogin, new API.APICallback<User>() {
            @Override
            public void onResponse(User response) throws JSONException {
                if (response != null) {
                    getMvpView().updateUI(response, action);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    private void getTotalRatting(int voucherId) {
        ApiRequest.ApiRequestTotalRatting paramTotalRatting = new ApiRequest.ApiRequestTotalRatting(voucherId);
        API.getTotalRatting(paramTotalRatting, new API.APICallback<RatingResponse>() {
            @Override
            public void onResponse(RatingResponse response) throws JSONException {
                if (response != null) {
                    getMvpView().displayTotalRatting(response);
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}
