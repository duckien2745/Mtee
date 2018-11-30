
package kienpd.com.mtee.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("countFollow")
    @Expose
    private int countFollow;
    @SerializedName("countVoucher")
    @Expose
    private int countVoucher;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCountFollow() {
        return countFollow;
    }

    public void setCountFollow(int countFollow) {
        this.countFollow = countFollow;
    }

    public int getCountVoucher() {
        return countVoucher;
    }

    public void setCountVoucher(int countVoucher) {
        this.countVoucher = countVoucher;
    }
}
