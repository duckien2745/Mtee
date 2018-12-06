package kienpd.com.mtee.data;


import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Message;
import kienpd.com.mtee.data.model.Messager;
import kienpd.com.mtee.data.model.RatingResponse;
import kienpd.com.mtee.data.model.StatusLikeSaveRating;
import kienpd.com.mtee.data.model.Store;
import kienpd.com.mtee.data.model.User;
import kienpd.com.mtee.data.model.UserCode;
import kienpd.com.mtee.data.model.Voucher;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class APIServices {

    public interface Services {

        @POST(APIDefinition.GetListVoucherNewest.PATH)
        Call<List<Voucher>> PostListVoucherNewest(
                @Body ApiRequest.ApiRequestVoucher param);

        @POST(APIDefinition.GetListVoucherHighLight.PATH)
        Call<List<Voucher>> PostListVoucherHighLight(
                @Body ApiRequest.ApiRequestVoucher param);

        @POST(APIDefinition.GetListCollection.PATH)
        Call<List<Collection>> PostListCollection(@Body ApiRequest.ApiRequestCollection param);

        @POST(APIDefinition.GetVoucherById.PATH)
        Call<Voucher> PostVoucherById(@Body ApiRequest.ApiRequestVoucherById param);

        @POST(APIDefinition.GetStatusLikeSaveRating.PATH)
        Call<StatusLikeSaveRating> GetStatus(@Body ApiRequest.ApiRequestStatus param);

        @POST(APIDefinition.UpdateLike.PATH)
        Call<Message> UpdateLike(@Body ApiRequest.ApiRequestStatus param);

        @POST(APIDefinition.UpdateSave.PATH)
        Call<Message> UpdateSave(@Body ApiRequest.ApiRequestStatus param);

        @POST(APIDefinition.GetInfoUser.PATH)
        Call<User> GetInfoUser(@Body ApiRequest.ApiRequestUser param);

        @POST(APIDefinition.RateVoucher.PATH)
        Call<Message> RateVoucher(@Body ApiRequest.ApiRequestRating param);

        @POST(APIDefinition.GetTotalRatting.PATH)
        Call<RatingResponse> GetTotalRatting(@Body ApiRequest.ApiRequestTotalRatting param);

        @POST(APIDefinition.GetCode.PATH)
        Call<UserCode> GetCode(@Body ApiRequest.ApiRequestGetCode param);

        @POST(APIDefinition.GetDetailCollection.PATH)
        Call<List<Voucher>> GetVoucherByCollectionId(@Body ApiRequest.ApiRequestDetailCollection param);

        @POST(APIDefinition.GetVoucherTakenByUserId.PATH)
        Call<List<UserCode>> GetVoucherTakenByUserId(@Body ApiRequest.ApiRequestVoucherTakenByUserId param);

        @POST(APIDefinition.GetVoucherSaveByUserId.PATH)
        Call<List<Voucher>> GetVoucherSaveByUserId(@Body ApiRequest.ApiRequestVoucherSaveByUserId param);

        @POST(APIDefinition.GetStoreFollow.PATH)
        Call<List<Store>> GetStoreFollow(@Body ApiRequest.ApiRequestStoreFollow param);

        @POST(APIDefinition.GetStoreById.PATH)
        Call<Store> GetStoreById(@Body ApiRequest.ApiRequestStoreById param);

        @POST(APIDefinition.GetVoucherByStoreId.PATH)
        Call<List<Voucher>> GetVoucherByStoreId(@Body ApiRequest.ApiRequestStoreById param);

        @POST(APIDefinition.StatusUserFollow.PATH)
        Call<Message> UpdateStatusUserFollow(@Body ApiRequest.ApiRequestStatusUserFollow param);

        @POST(APIDefinition.GetStatusUserFollow.PATH)
        Call<Message> GetStatusUserFollow(@Body ApiRequest.ApiRequestStatusUserFollow param);

        @POST(APIDefinition.ActiveCode.PATH)
        Call<Messager> ActiveCode(@Body ApiRequest.ApiRequestActiveCode param);

        @POST(APIDefinition.CheckCode.PATH)
        Call<Messager> CheckCode(@Body ApiRequest.ApiRequestCheckCode param);

        @POST(APIDefinition.Login.PATH)
        Call<User> Login(@Body ApiRequest.ApiRequestLogin param);

        @POST(APIDefinition.UpdateUserData.PATH)
        Call<Message> UpdateUserData(@Body ApiRequest.ApiRequestUpdateDataUser param);

    }
}