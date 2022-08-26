package ca.smartkids.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("user")
    User user;

    public UserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
