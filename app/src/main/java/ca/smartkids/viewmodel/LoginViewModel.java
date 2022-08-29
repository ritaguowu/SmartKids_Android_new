package ca.smartkids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.LoadKidsResponse;
import ca.smartkids.model.UserResponse;
import ca.smartkids.model.User;
import ca.smartkids.repository.CheckAutoLoginRepository;
import ca.smartkids.repository.LoadKidsRepository;
import ca.smartkids.repository.LoginRepository;


public class LoginViewModel extends ViewModel {
    // Create a LiveData with a String
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    LoginRepository loginRepository;
    LoadKidsRepository loadKidsRepository;
    CheckAutoLoginRepository checkAutoLoginRepository;
    DataStoreManager dataInstance = DataStoreManager.getInstance();
    String token ="";

    public LoginViewModel() {
        //Initialize the LiveData
        mLoginResultMutableData.postValue("");
        loginRepository = new LoginRepository();
        loadKidsRepository = new LoadKidsRepository();
        checkAutoLoginRepository = new CheckAutoLoginRepository();
        dataInstance = DataStoreManager.getInstance();
    }

    public void login(String email, String password) {
        mLoginResultMutableData.postValue("Checking");
        loginRepository.loginRemote(new User(email, password), new LoginRepository.ILoginResponse() {

            public void onResponse(UserResponse userResponse) {
                mLoginResultMutableData.postValue("Login Success");
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginResultMutableData.postValue("Login failure: " + t.getLocalizedMessage());
            }
        });
    }

    public void loadKidsInfo() {
        mLoginResultMutableData.postValue("Loading Kids...");
        String token = GlobalData.getInstance().getUser().getAccess_token();
        String parentId = GlobalData.getInstance().getUser().getUser_id();
        loadKidsRepository.LoadKidsRemote(token, parentId, new LoadKidsRepository.ILoadKidsResponse() {
            @Override
            public void onResponse(LoadKidsResponse loadKidsResponse) {
                mLoginResultMutableData.postValue("Load Kids Success");
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

    public void checkIfAutoLogin() {
        mLoginResultMutableData.postValue("Loading user...");
        dataInstance.getStringValue("token", s -> {
            token = s;
            dataInstance.getStringValue("parent_id", p -> {
                checkLogin(p);
            });
        });
    }

    public void checkLogin(String p){
        checkAutoLoginRepository.checkAutoLogin(token, p, new CheckAutoLoginRepository.ICheckLoginResponse() {
            @Override
            public void onResponse(UserResponse userResponse) {
                mLoginResultMutableData.postValue("The use has been already logged in");
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginResultMutableData.postValue("The use doesn't logged in");
            }
        });
    }
}
