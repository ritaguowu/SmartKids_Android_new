package ca.smartkids.retrofit;

import java.util.List;

import ca.smartkids.model.LoadKidsResponse;
import ca.smartkids.model.LoginResponse;
import ca.smartkids.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("/api/v1/auth")
    Call<LoginResponse> login(@Body User user);

    @POST("/api/v1/parent")
    Call<LoginResponse> signup(@Body User user);

    @POST("/api/v1/kid/{parentId}")
    Call<LoginResponse> signupKid(@Path("parentId") String parentId, @Body User user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/api/v1/kids")
    Call<LoadKidsResponse> getKidsByParentId(@Query("parentId") String parentId, @Header("Authorization") String auth);

    @GET("/api/v1/parent/{email}")
    Call<List<LoginResponse>> updateParent(@Path("email") String email);

    @PUT("/api/v1/kid/{email}&{kid_name}")
    Call<List<LoginResponse>> updateKid(@Path("email") String email, @Body User user);

    @DELETE("/api/v1/kid/{email}&{kid_name}")
    Call<List<LoginResponse>> deleteKid(@Path("email") String email);
}
