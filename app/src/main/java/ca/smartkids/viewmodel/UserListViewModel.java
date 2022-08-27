package ca.smartkids.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;

public class UserListViewModel extends ViewModel {
    private MutableLiveData<List<User>> kids = new MutableLiveData<>();         // Will be used in RecyclerView
    public MutableLiveData<Boolean> kidsLoadError = new MutableLiveData<>();     // TextView for Possible error
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();          // ProgressBar Show loading progress

    public UserListViewModel(@NonNull Application application) {
        super((Closeable) application);
    }

    public void refresh(){
        User user1 = new User("aa", "sdfsdf");
        User user2 = new User("bb", "sdfsdf");

        ArrayList<User> kidsList = new ArrayList<>();
        kidsList.addAll(Arrays.asList(user1,user2));

        kids.setValue(kidsList);
        kidsLoadError.setValue(false);
        loading.setValue(false);
    }

}

