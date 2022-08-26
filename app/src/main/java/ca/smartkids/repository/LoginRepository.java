package ca.smartkids.repository;

import org.json.JSONObject;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.model.UserResponse;
import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    public LoginRepository(){
    }

    public void loginRemote(User user, ILoginResponse loginResponse){
        APIService loginService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<UserResponse> initiateLogin = loginService.login(user);

        initiateLogin.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    loginResponse.onResponse(response.body());
                    User user = response.body().getUser();
                    DataStoreManager.instance.saveStringData("token", user.access_token);
                    DataStoreManager.instance.saveStringData("parent_id", user.user_id);
                    DataStoreManager.instance.saveStringData("email", user.email);
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
