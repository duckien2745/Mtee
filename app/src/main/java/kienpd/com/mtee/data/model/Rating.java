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

    @SerializedName("timePost")
    @Expose
    private Long timePost;

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
        return timePost;
    }

    public Integer getStar() {
        return star;
    }
}
