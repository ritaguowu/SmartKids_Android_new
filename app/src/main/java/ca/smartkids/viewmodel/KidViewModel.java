package ca.smartkids.viewmodel;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import ca.smartkids.adapters.UserListAdapter;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KidViewModel {

    private User kid;
    private UserListViewModel viewModel;


    public KidViewModel(UserListViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void createKid(String kidName, String parentId){
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        String token = GlobalData.getInstance().getUser().getAccess_token();
        Call<UserResponse> call = retrofitService.createKid(kidName, parentId,"Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    User user= response.body().getUser();
                    kid = user;
                    insertKid();
                    GlobalData.getInstance().getKids().forEach(System.out::println);

                }else
                {
                    System.out.println("Load kid failed");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                System.out.println("System error");
            }
        });
    }

    public void removeKidRemote(User kid){
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        String token = GlobalData.getInstance().getUser().getAccess_token();
        String kidId = kid.getUser_id();
        Call<UserResponse> call = retrofitService.removeKid(kidId,"Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    removeKid(kid);
                    GlobalData.getInstance().getKids().forEach(System.out::println);

                }else
                {
                    System.out.println("Load kid failed");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                System.out.println("System error");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void insertKid(){
        User newKid = kid;
        viewModel.insertKid(newKid);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void removeKid(User kid){
        viewModel.removeKid(kid);
    }
}
