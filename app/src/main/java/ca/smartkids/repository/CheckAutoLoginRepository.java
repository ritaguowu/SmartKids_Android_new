package ca.smartkids.repository;

import org.json.JSONObject;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.model.UserResponse;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAutoLoginRepository {

    public CheckAutoLoginRepository(){}

    public void checkAutoLogin(String token, String parent_Id, CheckAutoLoginRepository.ICheckLoginResponse checkLoginResponse){
        APIService loginService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<UserResponse> reLogin = loginService.getUserById(parent_Id,"Bearer "+token);

        reLogin.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    checkLoginResponse.onResponse(response.body());
//                    User user = response.body().getUser();
//                    System.out.println(user);
                    DataStoreManager.instance.saveStringData("LoggedUser", "Yes");
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        checkLoginResponse.onFailure(new Throwable(jObjError.get("error").toString()));
                    } catch (Exception e) {
                        System.out.println("System Error");
                    }

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                checkLoginResponse.onFailure(t);
            }
        });
    }

    public interface ICheckLoginResponse{
        void onResponse(UserResponse userResponse);
        void onFailure(Throwable t);
    }
}
