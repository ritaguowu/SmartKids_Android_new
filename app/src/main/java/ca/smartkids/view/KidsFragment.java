package ca.smartkids.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ca.smartkids.R;
import ca.smartkids.adapters.UserListAdapter;
import ca.smartkids.data.GlobalData;
import ca.smartkids.databinding.FragmentKidsBinding;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.util.ImageUtil;
import ca.smartkids.viewmodel.KidViewModel;
import ca.smartkids.viewmodel.UserListViewModel;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class KidsFragment extends Fragment {
    private List<User> kidsList = new ArrayList<>();
    private UserListViewModel viewModel;
    private UserListAdapter userListAdapter;
    private KidViewModel kidViewModel;

    public KidsFragment() {
        super(R.layout.fragment_kids);
    }

    private FragmentKidsBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        viewModel.refresh();

        kidViewModel = new KidViewModel(viewModel);

        binding = FragmentKidsBinding.bind(view);

        if (GlobalData.getInstance().getUser().getImage().equals("")) {
            Drawable myDrawable = getResources().getDrawable(R.drawable.default_user);
            binding.cvImage.setImageDrawable(myDrawable);
        } else {
            Bitmap bitmap = ImageUtil.convertToBitmap(GlobalData.getInstance().getUser().getImage());
            binding.cvImage.setImageBitmap(bitmap);
        }
        binding.tvUserName.setText(GlobalData.getInstance().getUser().getUser_name());
        binding.tvEmail.setText(GlobalData.getInstance().getUser().getEmail());

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
//                R.layout.list_kid, R.id.kid_name, DayOfWeek);
//        binding.mySpinner.setAdapter(adapter);

        // Initialize RecyclerView ......................................
        binding.rvKidList.setLayoutManager(new LinearLayoutManager(getContext()));
        userListAdapter = new UserListAdapter(new ArrayList<>());
        binding.rvKidList.setAdapter(userListAdapter);

        observeViewModel();

        //Add Kid button
        binding.fabAddKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddKidFragment().show(getChildFragmentManager(), AddKidFragment.TAG);
                getChildFragmentManager().setFragmentResultListener("requestKey",
                        KidsFragment.this, new FragmentResultListener() {
                            @Override
                            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                                // We use a String here, but any type that can be put in a Bundle is supported
                                String result = bundle.getString("bundleKey");
                                System.out.println(result);
                            }
                        });
            }
        });
    }


    private void observeViewModel() {
        // Observe ViewModel MutableLiveData
        // Use lambda expression to define what to do with kids arrayList
        viewModel.kids.observe(getViewLifecycleOwner(), kids -> {
            if (kids != null && kids instanceof List) {
                binding.rvKidList.setVisibility(View.VISIBLE);
                userListAdapter.updateKidsList(kids);
            }
        });

        // Show error message
        viewModel.kidsLoadError.observe(getViewLifecycleOwner(), isError -> {
            if (isError != null && isError instanceof Boolean) {
                binding.tvNoRecords.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        // Show progress bar
        viewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null && isLoading instanceof Boolean) {
                binding.progressbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    binding.tvNoRecords.setVisibility(View.GONE);
                    binding.rvKidList.setVisibility(View.GONE);
                }
            }
        });
    }

}