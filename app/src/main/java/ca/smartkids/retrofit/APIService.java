package ca.smartkids.retrofit;

import java.util.List;

import ca.smartkids.model.LoadKidsResponse;
import ca.smartkids.model.UserResponse;
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
    Call<UserResponse> login(@Body User user);

    @POST("/api/v1/parent")
    @Headers({"Accept:application/json", "content-Type:application/json"})
//    @Headers({"Accept:application/json", "content-Type:application/json", "Authorization: Bearer"})
    Call<UserResponse> createUser(@Body User user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/api/v1/kid")
    Call<UserResponse> createKid(@Query("kidName") String kidName,
                                 @Query("parentId") String parentId,
                                 @Header("Authorization") String auth);

    @GET("/api/v1/user")
    Call<UserResponse> getUserById(@Query("token") String token, @Query("parentId") String parentId);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/api/v1/kids")
    Call<LoadKidsResponse> getKidsByParentId(@Query("parentId") String parentId, @Header("Authorization") String auth);

    @GET("/api/v1/parent/{email}")
    Call<List<UserResponse>> updateParent(@Path("email") String email);

    @PUT("/api/v1/kid/{email}&{kid_name}")
    Call<List<UserResponse>> updateKid(@Path("email") String email, @Body User user);

    @DELETE("/api/v1/kid/{email}&{kid_name}")
    Call<List<UserResponse>> deleteKid(@Path("email") String email);

    @PUT("/api/v1/points")
    Call<UserResponse> logout();
}
