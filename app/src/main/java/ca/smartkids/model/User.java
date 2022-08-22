package ca.smartkids.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("uer_name")
    public String userName;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("image")
    public String image;

    @SerializedName("parentId")
    public String parentId;

//    public String accessToken


    public User(String userName, String email, String password, String image, String parentId) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.image = image;
        this.parentId = parentId;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
