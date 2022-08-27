package ca.smartkids.viewmodel;

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

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<UserResponse> createNewUserLiveData;
    DataStoreManager dataInstance;

    public SignUpViewModel() {
        createNewUserLiveData = new MutableLiveData<>();
        dataInstance = DataStoreManager.getInstance();
    }

    public MutableLiveData<UserResponse> getCreateUserObserver() {
        return createNewUserLiveData;
    }

    public void createUser(User user) {
        APIService retrofitService = RetrofitClientInstance.getInstance().create(APIService.class);
        Call<UserResponse> call = retrofitService.createUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    createNewUserLiveData.postValue(response.body());
                    User user = response.body().getUser();
                    DataStoreManager.getInstance().saveStringData("token", response.body().getUser().getAccess_token());
                    DataStoreManager.getInstance().saveStringData("parent_id", response.body().getUser().getUser_id());
                    GlobalData.getInstance().setUser(user);
                } else {
                    createNewUserLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                createNewUserLiveData.postValue(null);
            }
        });
    }
}
