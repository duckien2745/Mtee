
package kienpd.com.mtee.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCode {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("code")
    @Expose
    private Code code;
    @SerializedName("timeGetCode")
    @Expose
    private Long timeGetCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Long getTimeGetCode() {
        return timeGetCode;
    }

    public void setTimeGetCode(Long timeGetCode) {
        this.timeGetCode = timeGetCode;
    }
}
