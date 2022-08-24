package ca.smartkids.viewmodel;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ca.smartkids.model.User;
import ca.smartkids.retrofit.APIService;
import ca.smartkids.retrofit.RetrofitClientInstance;
import retrofit2.Call;

public class UserListViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            String parentId = "";
            loadUsers(parentId);
        }
        return users;
    }

    public void loadUsers(String parentId) {
        APIService apiService = RetrofitClientInstance.getInstance().create(APIService.class);
//        Call<List<User>> call = apiService.getKidsByParentId(parentId);
    }

//    private final MutableLiveData<Item> selected = new MutableLiveData<Item>();
//
//    public void select(Item item) {
//        selected.setValue(item);
//    }
//
//    public LiveData<Item> getSelected() {
//        return selected;
//    }
}

//public class ListFragment extends Fragment {
//    private UserListViewModel model;
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        model = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);
//        itemSelector.setOnClickListener(item -> {
//            model.select(item);
//        });
//    }
//}
//
//public class DetailFragment extends Fragment {
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        UserListViewModel model = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);
//        model.getSelected().observe(getViewLifecycleOwner(), item -> {
//            // Update the UI.
//        });
//    }
//}
