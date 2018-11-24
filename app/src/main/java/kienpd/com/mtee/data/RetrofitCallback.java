package kienpd.com.mtee.data;

import android.support.annotation.NonNull;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback<T> implements Callback<T> {

    private static final String TAG = "RetrofitCallback";
    private static final int LOCAL_ERROR = 7777;
    private static final int SERVER_ERROR = 5555;

    private API.APICallback<T> mCallback;

    RetrofitCallback(API.APICallback<T> callback) {
        mCallback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {


        if (response.isSuccessful()) {
            try {
                mCallback.onResponse(response.body());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mCallback.onFailure(LOCAL_ERROR, TAG + "" + response.code());
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        mCallback.onFailure(SERVER_ERROR, TAG + "" + t.getMessage());
    }
}