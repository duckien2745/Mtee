
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
    @SerializedName("pricePicture")
    @Expose
    private String pricePicture;
    @SerializedName("percentDiscount")
    @Expose
    private Integer percentDiscount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("timeEnd")
    @Expose
    private Integer timeEnd;
    @SerializedName("timeStart")
    @Expose
    private Integer timeStart;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("pictures")
    @Expose
    private List<Picture> pictures = null;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("likeCount")
    @Expose
    private Integer likeCount;

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

    public String getPricePicture() {
        return pricePicture;
    }

    public void setPricePicture(String pricePicture) {
        this.pricePicture = pricePicture;
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

    public Integer getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Integer timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Integer timeStart) {
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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
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

}
