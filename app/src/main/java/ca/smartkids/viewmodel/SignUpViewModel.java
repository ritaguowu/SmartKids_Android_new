package ca.smartkids.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.repository.LoginRepository;
import ca.smartkids.repository.SignUpRepository;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<UserResponse> createNewUserLiveData;
    DataStoreManager dataInstance;
    SignUpRepository signUpRepository;

    public SignUpViewModel() {
        createNewUserLiveData = new MutableLiveData<>();
        dataInstance = DataStoreManager.getInstance();
        signUpRepository = new SignUpRepository();
        GlobalData.getInstance().setKids(new ArrayList<>());
    }

    public MutableLiveData<UserResponse> getCreateUserObserver() {
        return createNewUserLiveData;
    }

    public void createUser(User user) {

        signUpRepository.signUpRemote(user, new SignUpRepository.ISignUpResponse() {

            public void onResponse(UserResponse userResponse) {
                createNewUserLiveData.postValue(userResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                createNewUserLiveData.postValue(null);
            }
        });
    }

}
