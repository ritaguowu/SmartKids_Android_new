package ca.smartkids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ca.smartkids.model.LoginResponse;
import ca.smartkids.model.User;
import ca.smartkids.repository.LoginRepository;


public class LoginViewModel extends ViewModel {
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    LoginRepository loginRepository;

    public LoginViewModel(){
        loginRepository = new LoginRepository();
    }

    public void login(String email, String password){
        mLoginResultMutableData.postValue("Checking");
        loginRepository.loginRemote(new User(email, password), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mLoginResultMutableData.postValue("Login Success");
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginResultMutableData.postValue("Login failure: "+ t.getLocalizedMessage());
            }
        });
    }

    public LiveData<String> getLoginResult(){
        return mLoginResultMutableData;
    }
}
