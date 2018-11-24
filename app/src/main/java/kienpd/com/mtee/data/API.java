package kienpd.com.mtee.data;

import org.json.JSONException;

import java.util.List;

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

    public static void getListVoucherhighLight(ApiRequest.ApiRequestVoucher param, APICallback<List<Voucher>> callback) {
        client.PostListVoucherHighLight(param).enqueue(new RetrofitCallback<>(callback));
    }


//    public static void getSuggestSiteByCategoryId(String categoryId, String size, APICallback<List<SfiveSuggestSite>> callback) {
//        client.GetSuggestSiteByCategoryId(categoryId, size).enqueue(new SfiveRetrofitCallback<>(callback));
//    }
//
//    public static void getSuggestSite(String input, String size, APICallback<List<SfiveSuggestSite>> callback) {
//        client.GetSuggestSite(input, size).enqueue(new SfiveRetrofitCallback<>(callback));
//    }

}