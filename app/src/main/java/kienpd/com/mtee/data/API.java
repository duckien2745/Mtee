package kienpd.com.mtee.data;

import org.json.JSONException;

import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.StatusLikeSaveRating;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class API {

    public static final String HOST_DEV = "http://192.168.16.103:8080/";

    private static String getHost() {
        return HOST_DEV;
    }

    private static final APIServices.Services client = new Retrofit.Builder()
            .baseUrl(getHost())
            .addConverterFactory(GsonConverterFactory.create())
            .client(loggingClient())
            .build()
            .create(APIServices.Services.class);


    private static OkHttpClient loggingClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    public interface APICallback<T> {
        void onResponse(T response) throws JSONException;

        void onFailure(int code, String message);
    }

    public static void getListVoucherNewest(ApiRequest.ApiRequestVoucher param, APICallback<List<Voucher>> callback) {
        client.PostListVoucherNewest(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getListVoucherHighLight(ApiRequest.ApiRequestVoucher param, APICallback<List<Voucher>> callback) {
        client.PostListVoucherHighLight(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getListCollection(ApiRequest.ApiRequestCollection param, APICallback<List<Collection>> callback) {
        client.PostListCollection(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getVoucherById(ApiRequest.ApiRequestVoucherById param, APICallback<Voucher> callback) {
        client.PostVoucherById(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getStatus(ApiRequest.ApiRequestStatus param, APICallback<StatusLikeSaveRating> callback) {
        client.GetStatus(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void updateLike(ApiRequest.ApiRequestStatus param, APICallback<Message> callback) {
        client.UpdateLike(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void updateSave(ApiRequest.ApiRequestStatus param, APICallback<Message> callback) {
        client.UpdateSave(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getInfoUser(ApiRequest.ApiRequestUser param, APICallback<User> callback) {
        client.GetInfoUser(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void rateVoucher(ApiRequest.ApiRequestRating param, APICallback<Message> callback) {
        client.RateVoucher(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getTotalRatting(ApiRequest.ApiRequestTotalRatting param, APICallback<RatingResponse> callback) {
        client.GetTotalRatting(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getCode(ApiRequest.ApiRequestGetCode param, APICallback<UserCode> callback) {
        client.GetCode(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getVoucherByCollectionId(ApiRequest.ApiRequestDetailCollection param, APICallback<List<Voucher>> callback) {
        client.GetVoucherByCollectionId(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getVoucherTakenByUserId(ApiRequest.ApiRequestVoucherTakenByUserId param, APICallback<List<UserCode>> callback) {
        client.GetVoucherTakenByUserId(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getVoucherSaveByUserId(ApiRequest.ApiRequestVoucherSaveByUserId param, APICallback<List<Voucher>> callback) {
        client.GetVoucherSaveByUserId(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getStoreFollow(ApiRequest.ApiRequestStoreFollow param, APICallback<List<Store>> callback) {
        client.GetStoreFollow(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getStoreById(ApiRequest.ApiRequestStoreById param, APICallback<Store> callback) {
        client.GetStoreById(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getVoucherByStoreId(ApiRequest.ApiRequestStoreById param, APICallback<List<Voucher>> callback) {
        client.GetVoucherByStoreId(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void updateStatusUserFollow(ApiRequest.ApiRequestStatusUserFollow param, APICallback<Message> callback) {
        client.UpdateStatusUserFollow(param).enqueue(new RetrofitCallback<>(callback));
    }

    public static void getStatusUserFollow(ApiRequest.ApiRequestStatusUserFollow param, APICallback<Message> callback) {
        client.GetStatusUserFollow(param).enqueue(new RetrofitCallback<>(callback));
    }
}