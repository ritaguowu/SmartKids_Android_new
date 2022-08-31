package ca.smartkids.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import ca.smartkids.R;
import ca.smartkids.data.GlobalData;
import ca.smartkids.databinding.FragmentMyProfileBinding;
import ca.smartkids.model.User;
import ca.smartkids.util.ImageUtil;
import ca.smartkids.viewmodel.MyProfileViewModel;


public class MyProfileFragment extends Fragment {

    FragmentMyProfileBinding binding;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    MyProfileViewModel myProfileViewModel;
    private String imageString = "";
    private User user;


    ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                if (data != null) {
                                    Bundle extras = data.getExtras();
                                    Bitmap thumbnail = (Bitmap) extras.get("data");
                                    binding.ivPlaceImage.setImageBitmap(thumbnail);

                                    imageString = ImageUtil.convertToString(thumbnail);
                                }
                            }
                        }
                    });

    public MyProfileFragment() {
        super(R.layout.fragment_my_profile);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentMyProfileBinding.bind(view);
        myProfileViewModel =  new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);

        binding.etName.setText(GlobalData.getInstance().getUser().getUser_name());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = createNewUser();
                System.out.println(user.getUser_name());
                GlobalData.getInstance().setUser(user);
                getParentFragmentManager().popBackStack();
            }
        });

        binding.tvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, MY_CAMERA_REQUEST_CODE);
                    }
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLauncher.launch(cameraIntent);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(cameraIntent);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

    private User createNewUser() {
        String name = binding.etName.getText().toString().trim();
        if (name.equals("")) {
            name = GlobalData.getInstance().getUser().getUser_name();
        }

        String password = "";
        String password1 = binding.etPassword1.getText().toString().trim();
        String password2 = binding.etPassword2.getText().toString().trim();
        if (!password1.equals("") && !password2.equals("")) {
            if (!password1.equals(password2)) {
                Toast.makeText(getActivity(), "passwords are not same", Toast.LENGTH_LONG).show();
            } else {
                password = password1;
            }
        }
        if (imageString.equals("")) {
            imageString = GlobalData.getInstance().getUser().getImage();
        }
        String email = GlobalData.getInstance().getUser().getEmail();

        user = new User(name, email, password, imageString);

        System.out.println(name + " " + email + "" + password);
        System.out.println(GlobalData.getInstance().getUser().getAccess_token());

        myProfileViewModel.updateParent(user);
        return user;
    }

}