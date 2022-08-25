package ca.smartkids.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadKidsResponse {
    @SerializedName("user")
    List<User> users;

    public LoadKidsResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
