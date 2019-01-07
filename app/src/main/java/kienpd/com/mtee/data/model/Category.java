
package kienpd.com.mtee.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.com.viettel.vtcc.browser.dragflowlayout.IDraggable;

public class Category implements IDraggable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    private Boolean isShow = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean isDraggable() {
        return false;
    }
}
