package ca.smartkids.repository;

import static java.lang.System.out;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.util.List;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.model.LoadKidsResponse;
import ca.smartkids.model.LoginResponse;
import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoadKidsRepository {
    public LoadKidsRepository() {
    }

    public void LoadKidsRemote(String token, String parent_Id, ILoadKidsResponse loadKidsResponse) {

        APIService loginService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<LoadKidsResponse> initiateLogin = loginService.getKidsByParentId(parent_Id, "Bearer "+token);

        initiateLogin.enqueue(new Callback<LoadKidsResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<LoadKidsResponse> call, Response<LoadKidsResponse> response) {
                if (response.isSuccessful()){
                    loadKidsResponse.onResponse(response.body());
                    LoadKidsResponse userList = (LoadKidsResponse) response.body();
                    List<User> users = userList.getUsers();
                    users.forEach(System.out::println);
//                    DataStoreManager.instance.saveStringData("user_id", userList.getUsers());
//                    DataStoreManager.instance.saveStringData("image", userList.get(0).getUser().image);
//                    DataStoreManager.instance.saveStringData("user_name", userList.getUsers().get(0).user_name);
                }
                else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        loadKidsResponse.onFailure(new Throwable(jObjError.get("error").toString()));
                    } catch (Exception e) {
                        out.println("System Error");
                    }

                }
            }

            @Override
            public void onFailure(Call<LoadKidsResponse> call, Throwable t) {
                loadKidsResponse.onFailure(t);
            }

        });
    }

    public interface ILoadKidsResponse{
        void onResponse(LoadKidsResponse loadKidsResponse);
        void onFailure(Throwable t);
    }
}
