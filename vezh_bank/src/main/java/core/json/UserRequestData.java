package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class UserRequestData {
    @Expose
    private int userId;

    @Expose
    private String detail;

    public UserRequestData(int userId, String detail) {
        this.userId = userId;
        this.detail = detail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
