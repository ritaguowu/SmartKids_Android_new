package ca.smartkids.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ca.smartkids.model.User;

public class UserListViewModel extends ViewModel {
    private MutableLiveData<List<User>> userList;

    public UserListViewModel(){
        userList = new MutableLiveData<>();
    }

    public MutableLiveData<List<User>> getUserListObserver(){
        return userList;
    }

    public void makeApiCall(){
//        APIService apiService = RetrofitClientInstance.getInstance().create(APIService.class);
//        Call<List<User>> call = apiService.getKidsByParentId
    }

}
