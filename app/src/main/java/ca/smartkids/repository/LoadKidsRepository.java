package ca.smartkids.repository;

import static java.lang.System.out;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.LoadKidsResponse;
import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadKidsRepository {
    GlobalData globalData;

    public LoadKidsRepository() {
    }

    public void LoadKidsRemote(String token, String parent_Id, ILoadKidsResponse loadKidsResponse) {

        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<LoadKidsResponse> call = retrofitService.getKidsByParentId(parent_Id, "Bearer " + token);

        call.enqueue(new Callback<LoadKidsResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<LoadKidsResponse> call, Response<LoadKidsResponse> response) {
                if (response.isSuccessful()) {
                    loadKidsResponse.onResponse(response.body());
                    LoadKidsResponse userList = response.body();
                    List<User> kidsList = userList.getUsers();
                    globalData.getInstance().setKids(kidsList);
                } else {
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

    public interface ILoadKidsResponse {
        void onResponse(LoadKidsResponse loadKidsResponse);

        void onFailure(Throwable t);
    }
}
