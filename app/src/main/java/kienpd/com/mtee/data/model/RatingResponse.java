
package kienpd.com.mtee.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingResponse {

    @SerializedName("rating5star")
    @Expose
    private Integer rating5star;
    @SerializedName("rating4star")
    @Expose
    private Integer rating4star;
    @SerializedName("rating3star")
    @Expose
    private Integer rating3star;
    @SerializedName("rating2star")
    @Expose
    private Integer rating2star;
    @SerializedName("rating1star")
    @Expose
    private Integer rating1star;

    public Integer getRating5star() {
        return rating5star;
    }

    public void setRating5star(Integer rating5star) {
        this.rating5star = rating5star;
    }

    public Integer getRating4star() {
        return rating4star;
    }

    public void setRating4star(Integer rating4star) {
        this.rating4star = rating4star;
    }

    public Integer getRating3star() {
        return rating3star;
    }

    public void setRating3star(Integer rating3star) {
        this.rating3star = rating3star;
    }

    public Integer getRating2star() {
        return rating2star;
    }

    public void setRating2star(Integer rating2star) {
        this.rating2star = rating2star;
    }

    public Integer getRating1star() {
        return rating1star;
    }

    public void setRating1star(Integer rating1star) {
        this.rating1star = rating1star;
    }

}
