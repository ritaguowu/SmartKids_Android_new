package ca.smartkids.retrofit;

import java.util.List;

import ca.smartkids.model.LoginResponse;
import ca.smartkids.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @POST("/api/v1/auth")
    Call<LoginResponse> login(@Body User user);

    @POST("/api/v1/parent")
    Call<LoginResponse> signup(@Body User user);

    @POST("/api/v1/kid/{parentId}")
    Call<LoginResponse> signupKid(@Path("parentId") String parentId, @Body User user);

    @GET("/api/v1/kids/{parentId}")
    Call<List<LoginResponse>> getKidsByParentId(@Path("parentId") String parentId);

    @GET("/api/v1/parent/{email}")
    Call<List<LoginResponse>> updateParent(@Path("email") String email);

    @PUT("/api/v1/kid/{email}&{kid_name}")
    Call<List<LoginResponse>> updateKid(@Path("email") String email, @Body User user);

    @DELETE("/api/v1/kid/{email}&{kid_name}")
    Call<List<LoginResponse>> deleteKid(@Path("email") String email);
}
