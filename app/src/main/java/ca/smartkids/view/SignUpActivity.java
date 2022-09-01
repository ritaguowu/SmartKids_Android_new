package ca.smartkids.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.smartkids.R;
import ca.smartkids.databinding.ActivityLoginBinding;
import ca.smartkids.databinding.ActivitySignUpBinding;
import ca.smartkids.model.User;
import ca.smartkids.model.UserResponse;
import ca.smartkids.viewmodel.SignUpViewModel;


public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        initViewModel();

        //Make app use full screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        setupActionBar();

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });

    }

    private void initViewModel(){
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpViewModel.getCreateUserObserver().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse == null) {
                    Toast.makeText(SignUpActivity.this, "Failed to create a new user", Toast.LENGTH_LONG).show();
//
                }
                else{
                    System.out.println(userResponse);
                    Toast.makeText(SignUpActivity.this, "Successfully created a new user", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this, ParentMainActivity.class));
                }
            }
        });
    }

    private void setupActionBar(){
        setSupportActionBar(binding.toolbarSignupActivity);
        binding.toolbarSignupActivity.setNavigationIcon(R.drawable.ic_black_back_24dp);
        binding.toolbarSignupActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createNewUser(){
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        User user = new User(name, email, password);
        signUpViewModel.createUser(user);
    }


}