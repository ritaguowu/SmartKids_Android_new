package ca.smartkids.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import ca.smartkids.R;
import ca.smartkids.data.GlobalData;
import ca.smartkids.databinding.FragmentSettingsBinding;
import ca.smartkids.model.UserResponse;
import ca.smartkids.util.ImageUtil;
import ca.smartkids.viewmodel.LogoutViewModel;
import ca.smartkids.viewmodel.MyProfileViewModel;



public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    MyProfileViewModel myProfileViewModel;
    LogoutViewModel logoutViewModel = new LogoutViewModel();

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentSettingsBinding.bind(view);
        myProfileViewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);

        if (GlobalData.getInstance().getUser().getImage().equals("")) {
            Drawable myDrawable = getResources().getDrawable(R.drawable.default_user);
            binding.cvImage.setImageDrawable(myDrawable);
        } else {
            Bitmap bitmap = ImageUtil.convertToBitmap(GlobalData.getInstance().getUser().getImage());
            binding.cvImage.setImageBitmap(bitmap);
        }

        binding.tvUserName.setText(GlobalData.getInstance().getUser().getUser_name());
        binding.tvEmail.setText(GlobalData.getInstance().getUser().getEmail());

        myProfileViewModel.getUpdateUserObserver().observe(getViewLifecycleOwner(), new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse != null && userResponse.getUser()!=null) {
                    binding.tvUserName.setText(GlobalData.getInstance().getUser().getUser_name());
                    Bitmap bitmap = ImageUtil.convertToBitmap(GlobalData.getInstance().getUser().getImage());
                    binding.cvImage.setImageBitmap(bitmap);
                }
            }
        });

        //Add Kid button
        binding.tvMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, myProfileFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exit = logoutViewModel.logOut();
                if (exit){
                    System.exit(0);
                }

            }
        });

    }


}