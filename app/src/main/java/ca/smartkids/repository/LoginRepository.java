package ca.smartkids.repository;

import org.json.JSONObject;

import ca.smartkids.model.LoginResponse;
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
        Call<LoginResponse> initiateLogin = loginService.login(user);

        initiateLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    loginResponse.onResponse(response.body());
//                    User user = response.body().getUser();

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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });
    }

    public interface ILoginResponse{
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable t);
    }
}
