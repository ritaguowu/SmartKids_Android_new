package ca.smartkids.repository;

import org.json.JSONObject;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutRepository {
    public LogoutRepository(){}

    public void logoutRemote(User user, LogoutRepository.ILogOutResponse logoutResponse){
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<UserResponse> call = retrofitService.login(user);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    logoutResponse.onResponse(response.body());
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        logoutResponse.onFailure(new Throwable(jObjError.get("error").toString()));
                    } catch (Exception e) {
                        System.out.println("System Error");
                    }

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                logoutResponse.onFailure(t);
            }
        });
    }

    public interface ILogOutResponse{
        void onResponse(UserResponse userResponse);
        void onFailure(Throwable t);
    }

}
