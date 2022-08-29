package ca.smartkids.viewmodel;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import ca.smartkids.data.GlobalData;
import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;

public class UserListViewModel extends AndroidViewModel {
    public MutableLiveData<List<User>> kids = new MutableLiveData<>();         // Will be used in RecyclerView
    public MutableLiveData<Boolean> kidsLoadError = new MutableLiveData<>();     // TextView for Possible error
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();          // ProgressBar Show loading progress

    public UserListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh(){

        ArrayList<User> kidsList = new ArrayList<>();
        kidsList.addAll(GlobalData.getInstance().getKids());

        kids.setValue(kidsList);
        kidsLoadError.setValue(false);
        loading.setValue(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertKid(User kid){
        ArrayList<User> kidsList = (ArrayList<User>) GlobalData.getInstance().getKids();
        kidsList.add(kid);
//        GlobalData.getInstance().getKids().forEach(System.out::println);

        kids.setValue(kidsList);
        kidsLoadError.setValue(false);
        loading.setValue(false);
    }

}

