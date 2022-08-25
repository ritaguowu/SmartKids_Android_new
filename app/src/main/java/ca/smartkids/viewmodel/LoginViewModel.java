package ca.smartkids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.model.LoadKidsResponse;
import ca.smartkids.model.LoginResponse;
import ca.smartkids.model.User;
import ca.smartkids.repository.LoadKidsRepository;
import ca.smartkids.repository.LoginRepository;


public class LoginViewModel extends ViewModel {
    // Create a LiveData with a String
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    LoginRepository loginRepository;
    LoadKidsRepository loadKidsRepository;
    DataStoreManager dataInstance;
    String token = "";

    public LoginViewModel() {
        //Initialize the LiveData
        mLoginResultMutableData.postValue("");
        loginRepository = new LoginRepository();
        loadKidsRepository = new LoadKidsRepository();
        dataInstance = DataStoreManager.instance;
    }

    public void login(String email, String password) {
        mLoginResultMutableData.postValue("Checking");
        loginRepository.loginRemote(new User(email, password), new LoginRepository.ILoginResponse() {

            public void onResponse(LoginResponse loginResponse) {
                mLoginResultMutableData.postValue("Login Success");
                dataInstance.getStringValue("token", s -> {
                    token = s;
                    dataInstance.getStringValue("parent_id", p -> {
                        loadKidsInfo(p);
                    });
//                    System.out.println("Test DataStore: token " + s);
                });
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginResultMutableData.postValue("Login failure: " + t.getLocalizedMessage());
            }
        });
    }

    public void loadKidsInfo(String parentId) {
        mLoginResultMutableData.postValue("Loading Kids...");
        loadKidsRepository.LoadKidsRemote(token, parentId, new LoadKidsRepository.ILoadKidsResponse() {
            @Override
            public void onResponse(LoadKidsResponse loadKidsResponse) {
                mLoginResultMutableData.postValue("Load Kids Success");
                dataInstance.getStringValue("user_name", s -> {
                    System.out.println("Kids' name is: "+ s);
                });
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginResultMutableData.postValue("Load kids failure: " + t.getLocalizedMessage());
            }

        });
    }

    public LiveData<String> getLoginResult() {
        return mLoginResultMutableData;
    }

}
