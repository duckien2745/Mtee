package kienpd.com.mtee.ui.home.voucher;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;

import kienpd.com.mtee.data.API;
import kienpd.com.mtee.data.ApiRequest;
import kienpd.com.mtee.data.model.Address;
import kienpd.com.mtee.data.model.Code;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import kienpd.com.mtee.ui.base.BasePresenter;
import kienpd.com.mtee.ui.home.rules.RulesMvpPresenter;
import kienpd.com.mtee.ui.home.rules.RulesMvpView;

public class VoucherPresenter<V extends VoucherMvpView> extends BasePresenter<V>
        implements VoucherMvpPresenter<V> {

    @Override
    public void getInfoVoucherUser(int detailId, int userId) {
        ApiRequest.ApiRequestGetCode requestGetCode = new ApiRequest.ApiRequestGetCode(userId, detailId);
        API.getCode(requestGetCode, new API.APICallback<UserCode>() {
            @Override
            public void onResponse(UserCode response) throws JSONException {
                if (response != null) {
                    Code code = response.getCode();
                    User user = response.getUser();

                    if (user != null && code != null) {
                        Voucher voucher = code.getVoucher();

                        String title = voucher.getTitle();
                        Log.d("uednued",title);
                        String pictureCover = voucher.getCoverPicture();
                        int countLike = voucher.getLikeCount();

                        Store store = voucher.getStore();
                        String nameStore = store.getName();
                        Address address = store.getAddress();
                        String sAddress = "";
                        if (address != null) {
                            sAddress = address.getNo() + "," + address.getStreet() + "," + address.getDistrict() + "," + address.getCity();
                        }
                        String sCode = code.getCode();

                        //todo
                        String dateVoucher = "17 tháng 12 2018";
                        String phone = "0969.056.804";

                        String nameUser = user.getName();
                        String email = user.getEmail();

                        getMvpView().displayView(title,pictureCover,countLike,sCode,nameStore,sAddress,dateVoucher,nameUser,phone,email);

                    }
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    @Override
    public void shareVoucher(Context context, String title, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, title));
    }

    @Override
    public void showDescriptionVoucher(int detailId) {
        //todo
    }

    @Override
    public void showDetailVoucher(int detailId) {
        //todo
    }

    @Override
    public void copyCode(String code) {
        //todo
    }

    @Override
    public void activeVoucher() {
        //todo
    }
}

