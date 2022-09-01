package ca.smartkids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileViewModel extends ViewModel {
    private MutableLiveData<UserResponse> updateUserLiveData;

    public MyProfileViewModel(){
        updateUserLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<UserResponse> getUpdateUserObserver(){
        return updateUserLiveData;
    }

    public LiveData<UserResponse> updateParent(User user){
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        String token = GlobalData.getInstance().getUser().getAccess_token();
        Call<UserResponse> call = retrofitService.updateParent(user.getEmail(), user, "Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    updateUserLiveData.setValue(response.body());
                    User user = response.body().getUser();
                    GlobalData.getInstance().setUser(user);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                updateUserLiveData.setValue(null);
                System.out.println("System error");
            }
        });
        return updateUserLiveData;
    }
}
