package kienpd.com.mtee.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("voucher")
    @Expose
    private Voucher voucher;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("time_post")
    @Expose
    private Long time_post;

    @SerializedName("star")
    @Expose
    private Integer star;


    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public String getComment() {
        return comment;
    }

    public Long getTimePost() {
        return time_post;
    }

    public Integer getStar() {
        return star;
    }
}
