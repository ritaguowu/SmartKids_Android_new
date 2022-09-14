package ca.smartkids.model;

import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    private String user_id;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;


    @SerializedName("image")
    private String image;

    @SerializedName("parentId")
    private String parentId;

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("points")
    private int points;

    public User(String user_id, String user_name, String email, String password, String image, String parentId, String access_token, int points) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.parentId = parentId;
        this.access_token = access_token;
        this.points = points;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String user_name, String email, String password) {
        this.user_name = user_name;
        this.password = password;
        this.email = email;
    }

    public User(String user_name, String email, String password, String image) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(String user_id, String user_name, String image, int points) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.image = image;
        this.points = points;
    }

    public User(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() >= 5;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", parentId='" + parentId + '\'' +
                ", access_token='" + access_token + '\'' +
                ", points=" + points +
                '}';
    }
}
