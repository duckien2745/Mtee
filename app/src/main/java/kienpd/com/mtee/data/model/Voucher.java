
package kienpd.com.mtee.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Voucher {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pricePictures")
    @Expose
    private List<String> pricePictures;
    @SerializedName("percentDiscount")
    @Expose
    private Integer percentDiscount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("timeEnd")
    @Expose
    private Long timeEnd;
    @SerializedName("timeStart")
    @Expose
    private Long timeStart;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("pictures")
    @Expose
    private List<String> pictures = null;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("likeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("ratingResponse")
    @Expose
    private RatingResponse ratingResponse;
    @SerializedName("remainingCode")
    @Expose
    private Integer remainingCode;

    @SerializedName("coverPicture")
    @Expose
    private String coverPicture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPricePictures() {
        return pricePictures;
    }

    public void setPricePictures(List<String> pricePictures) {
        this.pricePictures = pricePictures;
    }

    public Integer getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Integer percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public RatingResponse getRatingResponse() {
        return ratingResponse;
    }

    public void setRatingResponse(RatingResponse ratingResponse) {
        this.ratingResponse = ratingResponse;
    }

    public Integer getRemainingCode() {
        return remainingCode;
    }

    public void setRemainingCode(Integer remainingCode) {
        this.remainingCode = remainingCode;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }
}
