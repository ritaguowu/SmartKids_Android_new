package ca.smartkids.repository;

import org.json.JSONObject;

import java.util.List;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.model.LoginResponse;
import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadKidsRepository {
    public LoadKidsRepository() {
    }

    public void loadKidInfo(String token, String parent_Id, LoadKidsRepository.ILoginResponse loginResponse) {
        APIService loginService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<List<LoginResponse>> initiateLogin = loginService.getKidsByParentId(parent_Id, "Bearer "+token);

        initiateLogin.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                if (response.isSuccessful()){
//                    loginResponse.onResponse(response.body());
//                    User user = response.body().getUser();
//                    DataStoreManager.instance.saveStringData("token", user.access_token);
//                    DataStoreManager.instance.saveStringData("parent_id", user.user_id);
//                    DataStoreManager.instance.saveStringData("email", user.email);
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
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                loginResponse.onFailure(t);
            }

        });
    }

    public interface ILoginResponse{
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable t);
    }
}
