package ca.smartkids.repository;

import org.json.JSONObject;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.UserResponse;
import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    GlobalData globalData;

    public LoginRepository(){

    }

    public void loginRemote(User user, ILoginResponse loginResponse){
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<UserResponse> call = retrofitService.login(user);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    loginResponse.onResponse(response.body());
                    User user = response.body().getUser();
                    DataStoreManager.getInstance().saveStringData("token", user.getAccess_token());
                    DataStoreManager.getInstance().saveStringData("parent_id", user.getUser_id());
                    globalData.getInstance().setUser(user);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        loginResponse.onFailure(new Throwable(jObjError.get("error").toString()));
                    } catch (Exception e) {
                        System.out.println("System Error");
                    }

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });
    }

    public interface ILoginResponse{
        void onResponse(UserResponse userResponse);
        void onFailure(Throwable t);
    }
}
