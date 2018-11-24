package kienpd.com.mtee.data;


import java.util.List;

import kienpd.com.mtee.data.model.Collection;
import kienpd.com.mtee.data.model.Voucher;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class APIServices {

    public interface Services {

        @POST(APIDefinition.GetListVoucherNewest.PATH)
        Call<List<Voucher>> PostListVoucherNewest(
                @Body ApiRequest.ApiRequestVoucher param);

        @POST(APIDefinition.GetListVoucherHighLight.PATH)
        Call<List<Voucher>> PostListVoucherHighLight(
                @Body ApiRequest.ApiRequestVoucher param);

        @GET(APIDefinition.GetListCollection.PATH)
        Call<List<Collection>> GetListCollection();

//        @POST(SFiveAPIDefinition.GetLikeCount.PATH)
//        Call<SFiveLikeCount> PostLikeCount(
//                @Body SFiveApiRequest.SFiveApiRequestLikeCount sFiveParam);

//        @POST(SFiveAPIDefinition.GetShareCount.PATH)
//        Call<SFiveShareCount> PostShareCount(
//                @Body SFiveApiRequest.SFiveApiRequestShareCount sFiveParam);
//
//        @POST(SFiveAPIDefinition.GetVideoMetadata.PATH)
//        Call<SFiveVideoMetadata> PostVideoMetadata(
//                @Body SFiveApiRequest.SFiveApiRequestVideoMetadata sFiveParam);
//
//        @POST(SFiveAPIDefinition.GetListArticleByCategoryId.PATH)
//        Call<List<SFiveCategoryDatum>> PostListArticleByCategoryId(
//                @Body SFiveApiRequest.SFiveApiRequestArticleByCategoryId sFiveParam);
//
//        @POST(SFiveAPIDefinition.GetListArticleByTagName.PATH)
//        Call<List<SFiveCategoryDatum>> PostListArticleByTagName(
//                @Body SFiveApiRequest.SFiveApiRequestArticleByTag sFiveParam);
//
//        @POST(SFiveAPIDefinition.GetListRelatedArticleByArticleId.PATH)
//        Call<List<SFiveCategoryDatum>> PostListRelatedArticleByArticleId(
//                @Body SFiveApiRequest.SFiveApiRequestRelatedArticleById sFiveParam);
//
//        @GET(SFiveAPIDefinition.GetContenttag.PATH)
//        Call<List<SfiveContentTag>> GetListContentTag(@Query("from") int from, @Query("limit") int limit);
//
//        @GET(SFiveAPIDefinition.GetContentTagByKey.PATH)
//        Call<List<SfiveContentTag>> GetListContentTagByKey(@Query("input") String input, @Query("limit") int limit);
//
//
//        @POST(SFiveAPIDefinition.GetListContentFollowed.PATH)
//        Call<SFiveListHotContent> GetListHotContentFollowed(@Body SFiveApiRequest.SfiveApiRequestFollowedContent sfiveApiRequestFollowedContent);
//
//        @GET(SFiveAPIDefinition.GetCategoryArticle.PATH)
//        Call<List<SFiveCategory>> GetListCategory();
//
//
//        @POST(SFiveAPIDefinition.GetListContentFollowedByContentTag.PATH)
//        Call<SFiveListHotContent> GetListHotContentFollowedbyContentTag(@Body SFiveApiRequest.SfiveApiRequestFollowedContentByContentTag sfiveApiRequestFollowedContentByContentTag);
//
//        @POST(SFiveAPIDefinition.GetListContentFollowedByCategory.PATH)
//        Call<SFiveListHotContent> GetListHotContentFollowedbyCaterogy(@Body SFiveApiRequest.SfiveApiRequestFollowedContentbyCategory sfiveApiRequestFollowedContentbyCategory);
//
//        @GET(SFiveAPIDefinition.GetListNewsFromNotification.PATH)
//        Call<List<SFiveCategoryDatum>> GetListNewsFromNotification(@Query("id") String id);
//
//        @GET(SFiveAPIDefinition.GetListHotTags.PATH)
//        Call<ResponseBody> GetListHotTags();
//        @GET(SfiveAPIDefinition.GetSourcesArticle.PATH)
//        Call<List<SfiveSource>> GetListSourcesArticle();

//
//        @GET(SfiveAPIDefinition.GetListHotTags.PATH)
//        Call<List<SfiveHotTagArticle>> GetListHotTag();

//        @POST(SfiveAPIDefinition.GetListArticleByCategoryId.PATH)
//        Call<List<SfiveListSourceObject>> PostListArticleByCategoryById(
//                @Body JSONObject sFiveParam);
//
//        @POST(SfiveAPIDefinition.GetListArticleByTagName.PATH)
//        Call<List<SfiveListSourceObject>> PostListArticleByTagName(
//                @Body JSONObject sFiveParam);
//
//        @POST(SfiveAPIDefinition.GetListHotArticle.PATH)
//        Call<List<SfiveListSourceObject>> PostListHotArticle(
//                @Body JSONObject sFiveParam);
//
//        @POST(SfiveAPIDefinition.GetListRelatedArticleByArticleId.PATH)
//        Call<List<SfiveListSourceObject>> PostListRelatedArticleByArticleId(
//                @Body JSONObject sFiveParam);
//
//        @GET(SfiveAPIDefinition.GetSuggestSiteByCategoryId.PATH)
//        Call<List<SfiveSuggestSite>> GetSuggestSiteByCategoryId(
//                @Query(SfiveAPIDefinition.GetSuggestSiteByCategoryId.PARAM_CATEGORY_ID) String categoryId,
//                @Query(SfiveAPIDefinition.GetSuggestSiteByCategoryId.PARAM_SIZE) String size);
//
//        @GET(SfiveAPIDefinition.GetSuggestSite.PATH)
//        Call<List<SfiveSuggestSite>> GetSuggestSite(
//                @Query(SfiveAPIDefinition.GetSuggestSite.PARAM_INPUT) String input,
//                @Query(SfiveAPIDefinition.GetSuggestSite.PARAM_SIZE) String size);
    }
}