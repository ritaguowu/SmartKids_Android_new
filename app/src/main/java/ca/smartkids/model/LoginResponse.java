package ca.smartkids.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("user")
    User user;

    public LoginResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
