package kienpd.com.mtee.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusLikeSaveRating {

    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("save")
    @Expose
    private Integer save;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}